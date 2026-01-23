package br.com.shonenlog.controller;

import br.com.shonenlog.entity.Streaming;
import br.com.shonenlog.mapper.StreamingMapper;
import br.com.shonenlog.request.StreamingRequest;
import br.com.shonenlog.response.StreamingResponse;
import br.com.shonenlog.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shonenLog/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService service;

    @GetMapping
    public List<StreamingResponse> findAll() {
        List<Streaming> streamings = service.findAll();

        return streamings.stream()
                //.map(streaming -> StreamingMapper.toStreamingResponse(streaming)) -> sem lambda
                .map(StreamingMapper::toStreamingResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public StreamingResponse findById(@PathVariable Long id) {
        Optional<Streaming> optStreaming = service.findById(id);
            if (optStreaming.isPresent()) {
                return StreamingMapper.toStreamingResponse(optStreaming.get());
            }
            return null;
    }

    @PutMapping("/{id}")
    public StreamingResponse update(@PathVariable Long id, @RequestBody StreamingRequest streamingRequest) {
            Streaming updateStreaming = service.update(id, StreamingMapper.toStreaming(streamingRequest));

            return StreamingMapper.toStreamingResponse(updateStreaming);
    }

    @PostMapping
    public StreamingResponse save(@RequestBody StreamingRequest streaming) {

        Streaming newStreaming = StreamingMapper.toStreaming(streaming);
        Streaming savedStream = service.save(newStreaming);

        return StreamingMapper.toStreamingResponse(savedStream);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
         service.deleteById(id);
    }
}
