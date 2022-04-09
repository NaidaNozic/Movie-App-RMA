package com.example.myfirstapplication.data

fun favoriteMovies(): List<Movie> {
    return listOf(
        Movie(1,"Pride and prejudice",
            "Sparks fly when spirited Elizabeth Bennet meets single, rich, and proud Mr. Darcy. But Mr. Darcy reluctantly finds himself falling in love with a woman beneath his class. Can each overcome their own pride and prejudice?",
            "16.02.2005.","https://www.imdb.com/title/tt0414387/",
            "drama"),
        Movie(2,"Pulp Fiction",
            "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
            "14.10.1994.","https://www.imdb.com/title/tt0110912/",
            "crime"),
        Movie(3,"The Lord of the Rings",
            "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
            "10.12.2001","https://www.imdb.com/title/tt0120737/",
            "fantasy"),
        Movie(4,"Serenity",
            "The crew of the ship Serenity try to evade an assassin sent to recapture one of their members who is telepathic.",
            "30.09.2005","https://www.imdb.com/title/tt0379786/",
            "scifi"),
        Movie(5,"Shaun of the Dead",
            "A man's uneventful life is disrupted by the zombie apocalypse.",
            "09.04.2004","https://www.imdb.com/title/tt0365748/",
            "comedy"),
        Movie(6,"Watchmen",
            "In 1985 where former superheroes exist, the murder of a colleague sends active vigilante Rorschach into his own sprawling investigation, uncovering something that could completely change the course of history as we know it.",
            "23.02.2009.","https://www.imdb.com/title/tt0409459/",
            "action")
    )
}

fun recentMovies(): List<Movie> {
    return listOf(
        Movie(1,"The Courier",
            "Cold War spy Greville Wynne and his Russian source try to put an end to the Cuban Missile Crisis.",
            "17.05.2021.","https://www.imdb.com/title/tt8368512/",
            "thriller"),
        Movie(2,"Nobody",
            "A bystander who intervenes to help a woman being harassed by a group of men becomes the target of a vengeful drug lord.",
            "04.06.2021","https://www.imdb.com/title/tt7888964/",
            "crime"),
        Movie(3,"Black Widow",
            "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.",
            "07.04.2021.","https://www.imdb.com/title/tt3480822/",
            "adventure"),
        Movie(4,"Godzilla vs. Kong ",
            "The epic next chapter in the cinematic Monsterverse pits two of the greatest icons in motion picture history against one another - the fearsome Godzilla and the mighty Kong - with humanity caught in the balance.",
            "01.04.2021","https://www.imdb.com/title/tt5034838",
            "scifi"),
        Movie(5,"Top Gun: Maverick",
            "After more than thirty years of service as one of the Navy's top aviators, Pete Mitchell is where he belongs, pushing the envelope as a courageous test pilot and dodging the advancement in rank that would ground him.",
            "09.07.2021","https://www.imdb.com/title/tt1745960/",
            "drama"),
        Movie(6,"Luca",
            "On the Italian Riviera, an unlikely but strong friendship grows between a human being and a sea monster disguised as a human.",
            "18.06.2021.","https://www.imdb.com/title/tt12801262/",
            "family")
    )
}
fun actors():Map<String,List<String>>{
    return mapOf("Pride and prejudice" to listOf("Keira Knightley","Talulah Riley","Rosamund Pike","Jena Malone","Carey Mulligan","Donald Sutherland"),
                 "Pulp Fiction" to listOf("Tim Roth","Amanda Plummer","Laura Lovelace","John Travolta","Samuel L. Jackson","Phil LaMarr"),
                 "The Lord of the Rings" to listOf("Alan Howard","Noel Appleby","Sean Astin","Sala Baker","Sean Bean","Cate Blanchett"),
                 "Serenity" to listOf("Nathan Fillion","Gina Torres","Alan Tudyk","Morena Baccarin","Adam Baldwin","Jewel Staite"),
                 "Shaun of the Dead" to listOf("Simon Pegg","Kate Ashfield","Nick Frost","Lucy Davis","Dylan Moran"),
                 "Watchmen" to listOf("Malin Akerman", "Billy Crudup","Matthew Goode","Jackie Earle Haley")
    )
}
fun similarMovies():Map<String,List<String>>{
    return mapOf("Pride and prejudice" to listOf("Emma","Sense and Sensibility","Love & Friendship","Mansfield Park"),
                  "Pulp Fiction" to listOf("The Killing","From Dusk till Dawn","Get Shorty"),
                  "The Lord of the Rings" to listOf("The Chronicles of Narnia Series","King Arthur: Legend of the Sword","The Monkey King Series","Harry Potter Series"),
                  "Serenity" to listOf("Firefly","The Fifth Element","Farscape","Riddick"))
}