package com.example.santhe.util

import com.example.santhe.data.local.entity.Stall
import com.example.santhe.data.repository.StallRepository

object DataInitializer {
    suspend fun populateData(repository: StallRepository) {
        val stalls = listOf(
            // --- Original Data ---
            Stall(
                name = "Hampi Heritage Santhe",
                description = "Traditional weekly market near Virupaksha Temple. Best for local crafts and organic honey.",
                category = "Market",
                latitude = 15.3350,
                longitude = 76.4600,
                dayOfWeek = "Sunday",
                specialtyTags = "Crafts, Honey, Souvenirs"
            ),
            Stall(
                name = "Badami Khana-Vali",
                description = "Authentic North Karnataka Jolada Rotti meal. Famous for its spicy peanut chutney.",
                category = "Food",
                latitude = 15.9129,
                longitude = 75.6761,
                specialtyTags = "Jolada Rotti, Brinjal Curry, Chutney"
            ),
            Stall(
                name = "Melukote Handlooms",
                description = "Direct from weavers. Authentic hand-woven towels and dhotis.",
                category = "Craft",
                latitude = 12.6644,
                longitude = 76.6544,
                specialtyTags = "Handloom, Towels, Silk"
            ),
            Stall(
                name = "Mysuru Mylari Dosa",
                description = "Small, authentic mess serving the softest Mylari Dosas.",
                category = "Food",
                latitude = 12.3053,
                longitude = 76.6552,
                specialtyTags = "Dosa, Butter, Coffee"
            ),
            
            // --- New Bengaluru Data (15 Entries) ---
            Stall(
                name = "KR Market (City Market)",
                description = "The heart of Bengaluru's commerce. Famous for the early morning flower market and fresh spices.",
                category = "Market",
                latitude = 12.9664,
                longitude = 77.5768,
                dayOfWeek = "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday",
                specialtyTags = "Flowers, Vegetables, Spices",
                imageUrl = "https://images.unsplash.com/photo-1582234306487-3945ef502b4e?w=800"
            ),
            Stall(
                name = "Vidyarthi Bhavan",
                description = "Iconic eatery in Gandhi Bazaar. Known for its heritage and crispy Masala Dosas.",
                category = "Food",
                latitude = 12.9464,
                longitude = 77.5721,
                dayOfWeek = "Monday, Tuesday, Wednesday, Friday, Saturday, Sunday",
                specialtyTags = "Masala Dosa, Filter Coffee, Heritage",
                imageUrl = "https://images.unsplash.com/photo-1601050638917-3f363065a498?w=800"
            ),
            Stall(
                name = "Gandhi Bazaar Traditional Market",
                description = "One of the oldest shopping areas. Best for traditional items and festive shopping.",
                category = "Market",
                latitude = 12.9468,
                longitude = 77.5732,
                dayOfWeek = "Saturday, Sunday",
                specialtyTags = "Flowers, Traditional Snacks, Pooja items",
                imageUrl = "https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=800"
            ),
            Stall(
                name = "MTR Lalbagh",
                description = "Mavalli Tiffin Rooms. A legendary spot for Rava Idli and authentic South Indian meals.",
                category = "Food",
                latitude = 12.9548,
                longitude = 77.5841,
                dayOfWeek = "Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday",
                specialtyTags = "Rava Idli, Pulao, Filter Coffee",
                imageUrl = "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800"
            ),
            Stall(
                name = "Cauvery Handicrafts Emporium",
                description = "State-run store featuring the best of Karnataka's sandalwood and rosewood carvings.",
                category = "Craft",
                latitude = 12.9754,
                longitude = 77.6061,
                specialtyTags = "Sandalwood, Silk, Rosewood",
                imageUrl = "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=800"
            ),
            Stall(
                name = "CTR (Shri Sagar)",
                description = "Central Tiffin Room in Malleshwaram. The Benne Masala Dosa here is legendary.",
                category = "Food",
                latitude = 12.9982,
                longitude = 77.5703,
                specialtyTags = "Benne Masala Dosa, Bajji, Coffee",
                imageUrl = "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=800"
            ),
            Stall(
                name = "Malleshwaram 8th Main Market",
                description = "Classic residential market. Great for fresh produce and traditional household items.",
                category = "Market",
                latitude = 12.9961,
                longitude = 77.5714,
                dayOfWeek = "Tuesday, Friday",
                specialtyTags = "Vegetables, Bangles, Flowers",
                imageUrl = "https://images.unsplash.com/photo-1488459711616-2474c042918a?w=800"
            ),
            Stall(
                name = "Commercial Street",
                description = "Bustling shopping district. A mix of traditional shops and modern fashion.",
                category = "Market",
                latitude = 12.9822,
                longitude = 77.6084,
                dayOfWeek = "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday",
                specialtyTags = "Clothes, Jewelry, Footwear",
                imageUrl = "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=800"
            ),
            Stall(
                name = "Brahmin's Coffee Bar",
                description = "Standing-only cafe in Shankarapuram. Famous for its limited menu and amazing chutney.",
                category = "Food",
                latitude = 12.9427,
                longitude = 77.5684,
                specialtyTags = "Idli, Vada, Chutney, Coffee",
                imageUrl = "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=800"
            ),
            Stall(
                name = "Avenue Road Book Street",
                description = "A haven for book lovers. Streets lined with vendors selling rare and academic books.",
                category = "Market",
                latitude = 12.9698,
                longitude = 77.5796,
                dayOfWeek = "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday",
                specialtyTags = "Books, Stationery, Antiques",
                imageUrl = "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=800"
            ),
            Stall(
                name = "Pottery Town",
                description = "A unique settlement of potters. Visit during festivals to see incredible clay work.",
                category = "Craft",
                latitude = 13.0028,
                longitude = 77.6044,
                specialtyTags = "Pottery, Clay lamps, Earthenware",
                imageUrl = "https://images.unsplash.com/photo-1520406853248-1850ad3c6e2e?w=800"
            ),
            Stall(
                name = "Russell Market (Shivajinagar)",
                description = "Colonial-era market building. Known for high-quality meat and exotic fruits.",
                category = "Market",
                latitude = 12.9845,
                longitude = 77.6006,
                dayOfWeek = "Monday, Wednesday, Friday, Sunday",
                specialtyTags = "Exotic Fruits, Meat, Fish",
                imageUrl = "https://images.unsplash.com/photo-1533900298318-6b8da08a523e?w=800"
            ),
            Stall(
                name = "Veena Stores",
                description = "Tiny but mighty outlet in Malleshwaram. Their Puliyogare and Idlis are worth the queue.",
                category = "Food",
                latitude = 12.9967,
                longitude = 77.5738,
                specialtyTags = "Puliyogare, Shavige Bath, Idli",
                imageUrl = "https://images.unsplash.com/photo-1610192244261-3f33de3f55e4?w=800"
            ),
            Stall(
                name = "Chithrakala Parishath",
                description = "Art complex and gallery. Often hosts traditional handicraft fairs (Santes).",
                category = "Craft",
                latitude = 12.9863,
                longitude = 77.5816,
                specialtyTags = "Paintings, Mysore Art, Handicrafts",
                imageUrl = "https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?w=800"
            ),
            Stall(
                name = "Janapada Loka Museum Shop",
                description = "Promotes Karnataka's folk culture. Sells traditional puppets and rural artifacts.",
                category = "Craft",
                latitude = 12.6869,
                longitude = 77.4475,
                specialtyTags = "Puppets, Folk Art, Rural Crafts",
                imageUrl = "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=800"
            )
        )

        stalls.forEach { repository.addStall(it) }
    }
}
