package com.example.foodfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.DirectMessage
import com.example.MenuItem
import com.example.Notification
import com.example.Post
import com.example.Restaurant
import com.example.User
import com.example.foodfeed.ui.theme.FoodFeedTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodFeedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
                ) {
                    FeedView()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FeedView() {
        val posts = generateDummyPosts()
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Image(
                                painter = painterResource(id = R.drawable.foodfeedtitle),
                                contentDescription = "Title Image",
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(400.dp)// Adjust the width as needed
                            )

                        },
                        actions = {
                            // Right icons (notifications and dm)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clickable { /* Handle click if needed */ }
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                        .clickable{navController.navigate("notifications")}
                                )
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                        .clickable{navController.navigate("direct_messages")}
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = {
                BottomAppBar(
                    // backgroundColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        // Left icon (Home)
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                                .clickable { navController.navigate("home") }
                        )

                        // Center icon (Create)
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                                .clickable { /* Handle click if needed */ }
                        )

                        // Right icon (Profile)
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                                .clickable {
                                    navController.navigate("profile")
                                }
                        )
                    }
                }
            }, content = {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .padding(bottom = 5.dp) // Adjusted to match the height of the bottom bar
                ) {
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            // Search bar
                            OutlinedTextField(
                                value = "", // You can bind this to a state variable
                                onValueChange = { /* Handle value change */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                placeholder = { Text("Search") },
                                leadingIcon = {
                                    Icon(Icons.Default.Search, contentDescription = null)
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray
                                )
                            )
                            LazyColumn {
                                items(posts) { post ->
                                    PostItem(post)
                                }
                            }
                        }
                        composable("profile") {
                            // Assuming you have a sample user object
                            val user = User(
                                username = "JohnDoe",
                                bio = "Exploring the world of food!",
                                profilePictureRes = R.drawable.profileimg,
                                followers = 100,
                                following = 50,
                                posts = generateDummyPosts()
                            )
                            UserProfilePage(user = user)
                        }
                        composable("notifications") {
                            NotificationsPage(notifications = generateDummyNotifications())
                        }
                        composable("direct_messages") {
                            DmPage(dmList = generateDummyDirectMessages())
                        }
                    }
                }
            })
         }


    fun generateDummyPosts(): List<Post> {
        return List(10) { index ->
            Post(
                username = "User $index",
                time = "2 hours ago",
                imageRes = R.drawable.dummy_image,
                caption = "Delicious meal at a fantastic restaurant!",
                location = "City $index"
            )
        }
   }


    fun generateDummyNotifications(): List<Notification> {
        return List(5) { index ->
            Notification(
                title = "Notification $index",
                content = "This is the content of notification $index",
                time = "5m ago"
            )
        }
    }

    // Function to generate dummy direct messages
    fun generateDummyDirectMessages(): List<DirectMessage> {
        return List(5) { index ->
            DirectMessage(
                sender = "Sender $index",
                message = "This is a direct message from sender $index",
                time = "2 hours ago"
            )
        }
    }

    @Composable
    fun PostItem(post: Post) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.White)
        ) {
            // Post header (user info, time)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User profile picture
                Image(
                    painter = painterResource(id = R.drawable.profileimg),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = post.username, fontWeight = FontWeight.Bold)
                    Text(text = post.location,)
                }
            }
        }

            // Post content (image, caption, location)
            Image(
                painter = painterResource(id = post.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Post actions (like, comment, share)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = { /* Handle like action */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.yum_react_round),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                    Text(text = "Yum")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = { /* Handle comment action */ },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.AddCircle, contentDescription = null)
                    }
                    Text("Comment")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = { /* Handle share action */ },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null)
                    }
                    Text(text = "share")
                }
            }

            // Post caption
            Text(text = post.caption, modifier = Modifier.padding(10.dp))

            // Post
            Text(

                modifier = Modifier.padding(start = 10.dp),
                text = post.time
            )
    }



    //user profile page
    @Composable
    fun UserProfilePage(user: User) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User profile picture
            Image(
                painter = painterResource(id = user.profilePictureRes),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // User details
            Text(text = user.username, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = user.bio, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Followers, Following, Posts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user.followers.toString(), fontWeight = FontWeight.Bold)
                    Text(text = "Followers")
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user.following.toString(), fontWeight = FontWeight.Bold)
                    Text(text = "Following")
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user.posts.size.toString(), fontWeight = FontWeight.Bold)
                    Text(text = "Posts")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of Posts
            LazyColumn {
                items(user.posts) { post ->
                    PostItem(post)
                }
            }
        }
    }


    @Composable
    fun NotificationsPage(notifications: List<Notification>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Notifications",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(notifications) { notification ->
                    NotificationCard(notification)
                }
            }
        }
    }

    @Composable
    fun DmPage(dmList: List<DirectMessage>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Direct Messages",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(dmList) { message ->
                    DmCard(message)
                }
            }
        }
    }

    @Composable
    fun NotificationCard(notification: Notification) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = notification.content)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = notification.time, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }

    @Composable
    fun DmCard(message: DirectMessage) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Display sender's profile picture
                    Image(
                        painter = painterResource(id = R.drawable.profileimg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )

                    // Display sender's name
                    Text(
                        text = message.sender,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Display message content
                Text(text = message.message)
                Spacer(modifier = Modifier.height(8.dp))

                // Display time
                Text(text = message.time, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }

    // Function to generate a dummy restaurant
    fun generateDummyRestaurant(): Restaurant {
        return Restaurant(
            name = "Sample Restaurant",
            rating = 4,
            location = "123 Main Street, City",
            contactInfo = "Phone: 123-456-7890\nEmail: info@example.com",
            menuItems = generateDummyMenuItems(),
            posts = generateDummyPosts()
        )
    }

    // RestaurantProfilePage composable
    @Composable
    fun RestaurantProfilePage(restaurant: Restaurant) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Restaurant profile picture
            Image(
                painter = painterResource(id = R.drawable.profileimg),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Restaurant information
            Text(text = restaurant.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "Rating:", fontSize = 16.sp)
                repeat(restaurant.rating) {
                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
            Text(text = "Location: ${restaurant.location}", fontSize = 16.sp)
            Text(text = "Contact Info:\n${restaurant.contactInfo}", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Popular Items heading
            Text(text = "Popular Items", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            // Horizontal scrollable menu item list
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(restaurant.menuItems) { menuItem ->
                    MenuItemCard(menuItem)
                }
            }

            // List of posts
            LazyColumn {
                items(restaurant.posts) { post ->
                    PostItem(post)
                }
            }
        }
    }


    // MenuItemCard composable
    @Composable
    fun MenuItemCard(menuItem: MenuItem) {
        Card(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = menuItem.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${menuItem.price}",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }




//
//    @Preview
//    @Composable
//    fun FeedViewPreview() {
//        FeedView()
//    }
//
//
//    @Preview
//    @Composable
//    fun UserProfilePagePreview() {
//        // Assuming you have a sample user object
//        val user = User(
//            username = "JohnDoe",
//            bio = "Exploring the world of food!",
//            profilePictureRes = R.drawable.profileimg,
//            followers = 100,
//            following = 50,
//            posts = generateDummyPosts()
//        )
//        UserProfilePage(user = user)
//    }


    fun generateDummyMenuItems(): List<MenuItem> {
        return List(5) { index ->
            MenuItem(
                name = "Dish ${index + 1}",
                price = (5.0 + index).toDouble()
            )
        }
    }

    @Preview
    @Composable
    fun RestaurantProfilePagePreview() {
        // Assuming you have a sample restaurant object
        val restaurant = Restaurant(
            name = "Delicious Restaurant",
            rating = 4,
            location = "123 Main St, City",
            contactInfo = "123-456-7890",
            menuItems = generateDummyMenuItems(),
            posts = generateDummyPosts()
        )
        RestaurantProfilePage(restaurant = restaurant)
    }


}
