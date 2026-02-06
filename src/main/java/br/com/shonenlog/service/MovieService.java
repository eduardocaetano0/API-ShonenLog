package br.com.shonenlog.service;

import br.com.shonenlog.entity.Category;
import br.com.shonenlog.entity.Movie;
import br.com.shonenlog.entity.Streaming;
import br.com.shonenlog.repository.MovieRepository;
import br.com.shonenlog.request.MovieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;
    private CategoryService categoryService;
    private StreamingService streamingService;

    public Movie saveMovie(Movie movie){
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreamings(movie.getStreamings()));
        return movieRepository.save(movie);
    }

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    private List<Category> findCategories(List<Category> categories){
        List<Category> categorysFound = new ArrayList<>();
        categories.forEach(category -> categoryService.findById(category.getId()).ifPresent(categorysFound::add));
        return categorysFound;
    }

    private List<Streaming> findStreamings(List<Streaming> streamings){
        List<Streaming> streamingsFound = new ArrayList<>();
        streamings.forEach(streaming -> streamingService.findById(streaming.getId()).ifPresent(streamingsFound::add));
        return streamingsFound;
    }
    public Optional<Movie> update(Long movieId, Movie updateMovie){
        Optional<Movie> optMovie = movieRepository.findById(movieId);
        if(optMovie.isPresent()){
            List<Category> categories = this.findCategories(updateMovie.getCategories());
            List<Streaming> streamings = this.findStreamings(updateMovie.getStreamings());

            Movie movie = optMovie.get();
            movie.setTitle(movie.getTitle());
            movie.setDescription(movie.getDescription());
            movie.setReleaseDate(movie.getReleaseDate());
            movie.setRating(movie.getRating());


            movie.getCategories().clear();
            movie.getCategories().addAll(categories);

            movie.getStreamings().clear();
            movie.getStreamings().addAll(streamings);

            movieRepository.save(movie);
            return Optional.of(movie);
        }
        return Optional.empty();
    }

    public void  deleteById(Long id){
        movieRepository.deleteById(id);
    }
}
