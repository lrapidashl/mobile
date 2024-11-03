package com.example.films

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.bloco.faker.Faker
import com.example.films.databinding.ActivityMoviesListBinding

class MoviesListActivity : AppCompatActivity() {

    object Constants {
        const val COLUMN_COUNT = 3
        const val MOVIES_COUNT = 20
    }

    private lateinit var binding: ActivityMoviesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movies = generateFakeMovies(Constants.MOVIES_COUNT)

        binding.recyclerView.layoutManager = GridLayoutManager(this, Constants.COLUMN_COUNT)
        binding.recyclerView.addItemDecoration(Decoration(resources))
        binding.recyclerView.adapter = MoviesAdapter(movies) { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }

    private fun generateFakeMovies(count: Int): List<Movie> {
        val faker = Faker()
        val movies = mutableListOf<Movie>()

        for (i in 1..count) {
            val movie = Movie(
                title = faker.book.title(),
                description = faker.lorem.characters(), 
                rating = (1..5).random().toDouble(),
                imageUrl = "https://loremflickr.com/" + (480..1920).random() + "/" + (300..1080).random()
            )
            movies.add(movie)
        }

        return movies
    }
}
