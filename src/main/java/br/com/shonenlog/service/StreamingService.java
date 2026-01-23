package br.com.shonenlog.service;

import br.com.shonenlog.entity.Streaming;
import br.com.shonenlog.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> findAll() {
        return streamingRepository.findAll();
    }

    public Optional<Streaming> findById(Long id) {
        return streamingRepository.findById(id);
    }

    public Streaming save(Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public Streaming update(Long id,Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public void deleteById(Long id) {
        streamingRepository.deleteById(id);
    }
}
