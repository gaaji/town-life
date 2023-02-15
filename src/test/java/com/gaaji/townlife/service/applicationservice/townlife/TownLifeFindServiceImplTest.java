package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TownLifeFindServiceImplTest {

    @Mock private TownLifeRepository townLifeRepository;
    @Mock private TownLifeCounterRepository townLifeCounterRepository;
    @Mock private TownLifeFindEntityServiceImpl townLifeFindEntityService;
    @Mock private TownLifeFindCountServiceImpl townLifeFindCountService;
    @InjectMocks private TownLifeFindServiceImpl townLifeFindService;

    void create_town_life() {

    }


}