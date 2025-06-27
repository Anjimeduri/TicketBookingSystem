package com.tbs.services;

import lombok.extern.slf4j.Slf4j;
import com.tbs.entity.Show;
import com.tbs.repository.ShowRepository;
import com.tbs.services.serviceInterfaces.IShowService;

@Slf4j
public class ShowService extends BaseService<Show> implements IShowService {
    public ShowService(ShowRepository showRepository) {
        super(showRepository);
    }
}
