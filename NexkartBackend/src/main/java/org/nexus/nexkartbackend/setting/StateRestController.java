package org.nexus.nexkartbackend.setting;


import org.nexus.nexkartbackend.Repository.StateRepository;
import org.nexus.nexkartbackend.entity.Country;
import org.nexus.nexkartbackend.entity.State;
import org.nexus.nexkartbackend.entity.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class StateRestController {

    @Autowired private StateRepository repo;

    @GetMapping("/states/list_by_country/{id}")
    public List<StateDTO> listByCountry(@PathVariable("id") Integer countryId) {
        List<State> listStates = repo.findByCountryOrderByNameAsc(new Country(countryId));
        List<StateDTO> result = new ArrayList<>();

        for (State state : listStates) {
            result.add(new StateDTO(state.getId(), state.getName()));
        }

        return result;
    }

    @PostMapping("/states/save")
    public String save(@RequestBody State state) {
        State savedState = repo.save(state);
        return String.valueOf(savedState.getId());
    }

    @GetMapping("/states/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
    }
}