
    import android.annotation.SuppressLint
    import android.content.Context
    import android.location.Address
    import android.location.Geocoder
    import android.os.Build
    import androidx.core.content.ContextCompat
    import androidx.core.content.PermissionChecker
    import com.google.android.gms.location.*
    import kotlinx.coroutines.suspendCancellableCoroutine
    import java.io.IOException
    import java.util.Locale
    import kotlin.coroutines.resume
    import android.Manifest
    import com.example.weathertaskapp.R

    class LocationPermissionDeniedException(context: Context) :
        Exception(context.getString(R.string.error_permission_denied))

    class LocationUnavailableException(context: Context) :
        Exception(context.getString(R.string.error_location_unavailable))

    class GeocodingFailedException(context: Context) :
        Exception(context.getString(R.string.error_geocoding_failed))

    class LocationHelper(private val context: Context) {
        private val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        @SuppressLint("MissingPermission")
        suspend fun getCurrentCity(): String {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PermissionChecker.PERMISSION_GRANTED
            ) {
                throw LocationPermissionDeniedException(context)
            }

            var location = suspendCancellableCoroutine { cont ->
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { cont.resume(it) }
                    .addOnFailureListener { cont.resume(null) }
            }

            if (location == null) {

                location = suspendCancellableCoroutine { cont ->
                    val locationRequest = LocationRequest.create()
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                        .setInterval(1000)
                        .setNumUpdates(1)

                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(result: LocationResult) {
                                fusedLocationClient.removeLocationUpdates(this)
                                cont.resume(result.lastLocation)
                            }

                            override fun onLocationAvailability(availability: LocationAvailability) {
                                if (!availability.isLocationAvailable) {
                                    cont.resume(null)
                                }
                            }
                        },
                        android.os.Looper.getMainLooper()
                    )
                }
            }

            if (location == null) {
                throw LocationUnavailableException(context)
            }

            val geocoder = Geocoder(context, Locale.getDefault())

            val city = suspendCancellableCoroutine<String?> { cont ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1,
                        object : Geocoder.GeocodeListener {
                            override fun onGeocode(addresses: MutableList<Address>) {
                                cont.resume(addresses.firstOrNull()?.locality)
                            }

                            override fun onError(errorMessage: String?) {
                                cont.resume(null)
                            }
                        }
                    )
                } else {
                    try {
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        cont.resume(addresses?.firstOrNull()?.locality)
                    } catch (e: IOException) {
                        cont.resume(null)
                    }
                }
            }

            return city ?: throw GeocodingFailedException(context)
        }
    }