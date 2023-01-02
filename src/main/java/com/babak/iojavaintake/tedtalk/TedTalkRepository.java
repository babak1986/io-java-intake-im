package com.babak.iojavaintake.tedtalk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TedTalkRepository extends JpaRepository<TedTalk, Integer>, JpaSpecificationExecutor<TedTalk> {

}
