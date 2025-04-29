package com.nedrysystems.eventorias.utils.serviceInterface

/**
 * Interface for generating URLs for static maps.
 *
 * This interface defines the contract for services that generate URLs for static maps based on
 * geographical coordinates. The generated URL can be used to request static map images from
 * a map service (e.g., Google Maps Static API) with a specified zoom level and size.
 */
interface StaticMapUrlGenerator {

    /**
     * Generates a URL for a static map image based on the given parameters.
     *
     * This function generates a URL that can be used to request a static map image from a map
     * service. The map will be centered at the specified latitude and longitude, with the specified
     * zoom level and size. The resulting URL can be used to display the static map image in an
     * application or web page.
     *
     * @param latitude The latitude of the center of the map.
     * @param longitude The longitude of the center of the map.
     * @param zoom The zoom level of the map (default is 17). A higher value results in a more
     *             zoomed-in view.
     * @param sizePx The size of the map image in pixels, represented as a pair of width and height
     *               (default is 800x400 pixels).
     * @return A URL string that can be used to request the static map image.
     */
    fun generateUrl(
        latitude: Double,
        longitude: Double,
        zoom: Int = 17,
        sizePx: Pair<Int, Int> = 800 to 400
    ): String
}