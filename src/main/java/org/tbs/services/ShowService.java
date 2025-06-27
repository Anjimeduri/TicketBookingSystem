package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.Show;
import org.tbs.repository.ShowRepository;
import org.tbs.services.serviceInterfaces.IShowService;

@Slf4j
public class ShowService extends BaseService<Show> implements IShowService {
    public ShowService(ShowRepository showRepository) {
        super(showRepository);
    }
}
