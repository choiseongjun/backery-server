package com.bakery.pj.domain.breadstore;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BreadStoreRepositoryTest {

    @Autowired
    BreadstoreRepository breadstoreRepository;

    @After
    public void cleanUp() {
        //breadstoreRepository.deleteAll();
    }

    @Test
    public void 빵집저장_불러오기() {
        String entrpNm = "테스트 빵집";
        String reprsntMenuNm = "테스트 메뉴";
        byte[] image = new byte[0];

        breadstoreRepository.save(Breadstore.builder()
                .entrpNm(entrpNm)
                .reprsntMenuNm(reprsntMenuNm)
                .image(image)
                .build());

        List<Breadstore> breadstoreList = breadstoreRepository.findAll();

        Breadstore breadstore = breadstoreList.get(0);
        assertThat(breadstore.getEntrpNm()).isEqualTo(entrpNm);
        assertThat(breadstore.getReprsntMenuNm()).isEqualTo(reprsntMenuNm);
    }
}
