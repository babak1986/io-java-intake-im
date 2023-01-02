package com.babak.iojavaintake.tedtalk;

import com.babak.iojavaintake.excption.TedTalkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tedtalk")
@RequiredArgsConstructor
public class TedTalkController {

    private final TedTalkService service;

    @GetMapping("v1/search")
    public ResponseEntity search(@RequestParam(defaultValue = "") String title,
                                 @RequestParam(defaultValue = "") String author,
                                 @RequestParam(defaultValue = "0") Long likes,
                                 @RequestParam(defaultValue = "0") Long views) {
        return new ResponseEntity(service.search(new TedTalkDto(title, author, views, likes)), HttpStatus.OK);
    }

    @PostMapping("v1/create")
    public ResponseEntity create(@RequestBody TedTalkDto tedTalkDto) {
        try {
            TedTalk tedTalk = service.create(tedTalkDto);
            return new ResponseEntity(tedTalk, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("v1/update")
    public ResponseEntity update(@RequestBody TedTalkDto tedTalkDto) {
        try {
            TedTalk tedTalk = service.update(tedTalkDto);
            return new ResponseEntity(tedTalk, HttpStatus.OK);
        } catch (TedTalkNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("v1/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (TedTalkNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }
}
