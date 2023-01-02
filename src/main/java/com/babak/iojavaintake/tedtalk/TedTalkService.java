package com.babak.iojavaintake.tedtalk;

import com.babak.iojavaintake.base.BaseService;
import com.babak.iojavaintake.excption.TedTalkNotFoundException;
import com.babak.iojavaintake.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TedTalkService extends BaseService<TedTalk> implements CsvUtil<TedTalkDto> {

    private final TedTalkRepository repository;

    public long count() {
        return repository.count();
    }

    public TedTalk map(TedTalkDto tedTalkDto) {
        TedTalk tedTalk = new TedTalk();
        tedTalk.setId(tedTalkDto.getId());
        tedTalk.setTitle(tedTalkDto.getTitle());
        tedTalk.setAuthor(tedTalkDto.getAuthor());
        tedTalk.setDate(tedTalk.getDate());
        tedTalk.setLikes(tedTalkDto.getLikes());
        tedTalk.setViews(tedTalkDto.getViews());
        tedTalk.setLink(tedTalkDto.getLink());
        return tedTalk;
    }

    /**
     * Searches for TedTalk entity objects which are not logically deleted
     * Uses input TedTalkDto as filter model
     *
     * @param tedTalkDto
     * @return
     */
    @Cacheable(cacheNames = "tedTalks")
    public List<TedTalk> search(TedTalkDto tedTalkDto) {
        return repository.findAll(new TedTalkSpecification(tedTalkDto));
    }

    /**
     * Creates new TedTalk entity object
     *
     * @param tedTalkDto
     * @return
     */
    public TedTalk create(TedTalkDto tedTalkDto) {
        return repository.save(map(tedTalkDto));
    }

    /**
     * Update existing TedTalk entity object
     * If there is no entity with the id, it will throw a TedTalkNotFoundException
     *
     * @param tedTalkDto
     * @return
     * @throws TedTalkNotFoundException
     */
    public TedTalk update(TedTalkDto tedTalkDto) throws TedTalkNotFoundException {
        logger.info("Updating TedTalk entity object with id '%d'.", tedTalkDto.getId());
        TedTalk tedTalk = repository
                .findById(tedTalkDto.getId())
                .orElseThrow(TedTalkNotFoundException::new);
        tedTalk.setTitle(tedTalkDto.getTitle());
        tedTalk.setAuthor(tedTalkDto.getAuthor());
        tedTalk.setDate(tedTalk.getDate());
        tedTalk.setLikes(tedTalkDto.getLikes());
        tedTalk.setViews(tedTalkDto.getViews());
        tedTalk.setLink(tedTalkDto.getLink());
        return repository.save(tedTalk);
    }

    /**
     * Logically, deletes the TedTalk entity object by the given id,
     * if there is no entity with the id, it will throw a TedTalkNotFoundException
     *
     * @param id
     * @return
     * @throws TedTalkNotFoundException
     */
    public TedTalk delete(Integer id) throws TedTalkNotFoundException {
        logger.info("Deleting TedTalk entity object with id '%d'.", id);
        TedTalk tedTalk = repository.findById(id)
                .orElseThrow(TedTalkNotFoundException::new);
        tedTalk.setDeleted(true);
        return repository.save(tedTalk);
    }
}
