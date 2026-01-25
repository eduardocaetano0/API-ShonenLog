package br.com.shonenlog.controller;

import br.com.shonenlog.entity.Movie;
import br.com.shonenlog.mapper.MovieMapper;
import br.com.shonenlog.request.MovieRequest;
import br.com.shonenlog.response.MovieResponse;
import br.com.shonenlog.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shonenLog/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@RequestBody MovieRequest movieRequest){
        Movie saveMovie = movieService.saveMovie(MovieMapper.toMovie(movieRequest));

        return ResponseEntity.ok().body(MovieMapper.toMovieResponse(saveMovie));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll(){
        return ResponseEntity.ok(movieService.findAll()
                .stream()
                //movie -> MovieMapper.ToMovieResponse(movie)
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findByID(@PathVariable Long id){
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByID(@PathVariable Long id){
        movieService.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
