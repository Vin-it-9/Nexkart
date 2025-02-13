package org.nexus.nexkartfrontend.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repo;

    public List<Setting> listAllSettings() {
        return (List<Setting>) repo.findAll();
    }


    public void saveAll(Iterable<Setting> settings) {
        repo.saveAll(settings);
    }

    public List<Setting> getGeneralSettings() {
        return repo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }


}