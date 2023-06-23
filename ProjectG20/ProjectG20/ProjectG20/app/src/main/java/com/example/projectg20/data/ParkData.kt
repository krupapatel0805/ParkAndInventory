package com.example.projectg20

object ParkData {
    val nationalParks = listOf(
        NationalParkData(
            1,
            "https://www.nps.gov/acad/index.htm",
            "Acadia National Park",
            "Acadia National Park protects the natural beauty of the highest rocky headlands along the Atlantic coastline of the United States, an abundance of habitats with high biodiversity, clean air and water, and a rich cultural heritage. Each year, more than 3.5 million people explore seven peaks above 1,000 feet, 158 miles of hiking trails, and 45 miles of carriage roads with 16 stone bridges.",
            44.35,
            -68.21,
            "ME",
            "PO Box 177, Bar Harbor, ME 04609, USA",
            listOf(
                "https://www.nps.gov/common/uploads/structured_data/3C7A9E9A-1DD8-B71B-0B1562B24A6B76C8.jpg",
                "https://www.nps.gov/common/uploads/structured_data/3C7AA43E-1DD8-B71B-0BBDB3A41F73977E.jpg"
            )
        ),
        NationalParkData(
            2,
            "https://www.nps.gov/akso/akarcres/units/AFGNWR.htm",
            "Arctic National Wildlife Refuge",
            "The Arctic National Wildlife Refuge is the largest protected wilderness in the United States. Located in the northeastern part of Alaska, the refuge is home to a wide variety of wildlife, including polar bears, caribou, and muskoxen. It is also an important breeding ground for migratory birds.",
            69.29,
            -144.13,
            "AK",
            "101 12th Ave, Fairbanks, AK 99701",
            listOf(
                "https://www.nps.gov/akso/akarcres/images/brochure/brochure.jpg",
                "https://www.nps.gov/akso/akarcres/images/brochure2/brochure2.jpg"
            )
        ),
        NationalParkData(
            3,
            "https://www.nps.gov/arch/index.htm",
            "Arches National Park",
            "Arches National Park is known for preserving over 2,000 natural sandstone arches, including the world-famous Delicate Arch. It is also home to a variety of other geological formations, including spires, fins, and balanced rocks.",
            38.68,
            -109.57,
            "UT",
            "PO Box 907, Moab, UT 84532",
            listOf(
                "https://www.nps.gov/arch/planyourvisit/images/learn/archinfo.jpg",
                "https://www.nps.gov/arch/planyourvisit/images/learn/archdiagram.jpg"
            )
        ),
        NationalParkData(
            4,
            "https://www.nps.gov/badl/index.htm",
            "Badlands National Park",
            "Badlands National Park protects a rugged landscape of steep canyons, towering spires, and sweeping prairies. The park is home to a variety of wildlife, including bison, pronghorns, bighorn sheep, and coyotes.",
            43.75,
            -102.50,
            "SD",
            "25216 Ben Reifel Road, Interior, SD 57750",
            listOf(
                "https://www.nps.gov/common/uploads/structured_data/3C7E5C5D-1DD8-B71B-0B7E1ACDCA04CC9F.jpg",
                "https://www.nps.gov/common/uploads/structured_data/3C7E5E7C-1DD8-B71B-0BCFE21511E27F25.jpg"
            )
        ),
        NationalParkData(
            5,
            "https://www.nps.gov/bibe/index.htm",
            "Big Bend National Park",
            "Big Bend National Park protects a rugged and remote region of West Texas where the Rio Grande River cuts through towering canyons and desert landscapes. Visitors can explore over 150 miles of trails, paddle along the river, or stargaze under some of the darkest skies in the country.",
            29.25,
            -103.25,
            "TX",
            "Big Bend National Park, TX, United States",
            listOf(
                "https://www.nps.gov/common/uploads/structured_data/3C855B2B-1DD8-B71B-0B9ACD8C8B55C1AC.jpg",
                "https://www.nps.gov/common/uploads/structured_data/3C855BFF-1DD8-B71B-0B9F9A6607A1D46B.jpg"
            )
        ),
    )
}

data class NationalParkData(
    val id: Int,
    val url: String,
    val fullName: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val stateCode: String,
    val address: String,
    val images: List<String>
)

