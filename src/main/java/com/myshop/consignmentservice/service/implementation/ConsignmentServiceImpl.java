package com.myshop.consignmentservice.service.implementation;

import com.myshop.consignmentservice.api.dto.ItemDto;
import com.myshop.consignmentservice.model.Consignment;
import com.myshop.consignmentservice.repository.ConsignmentRepository;
import com.myshop.consignmentservice.service.ConsignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ConsignmentServiceImpl implements ConsignmentService {

    private final ConsignmentRepository consignmentRepo;
    private final String itemsUrlAdress = "http://shop-service:8081/items";


    private boolean check(Long itemId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(itemId);

        final ResponseEntity<ItemDto> userResponse = restTemplate
                .exchange(itemsUrlAdress + "/dto/" + itemId,
                        HttpMethod.GET, userRequest, ItemDto.class);

        return userResponse.getStatusCode() != HttpStatus.NOT_FOUND;
    }


    public Consignment add(Long itemId, int quantity, Date date) throws IllegalArgumentException{
        if(!check(itemId)) throw new IllegalArgumentException("no such item");
        else{
            Consignment consignment = new Consignment(quantity, date, itemId);
            return consignmentRepo.save(consignment);}
    }

    public void deleteById(Long id) {
        consignmentRepo.deleteById(id);

    }

    public Consignment findById(Long id)throws IllegalArgumentException{
        final Optional<Consignment> consignment = consignmentRepo.findById(id);
        if(consignment.isEmpty()) throw new RuntimeException("Impossible to do (Sorry)");
        else return consignment.get();
    }



    public void update(Long id, Long itemId, int quantity, Date date) throws IllegalArgumentException{
        final Optional<Consignment> consignment = consignmentRepo.findById(id);
        if(consignment.isEmpty())throw new RuntimeException("Consignment not found (Sorry)");
        final Consignment consignmentReal = consignment.get();
        if (itemId != null && itemId != -1) {
            if (!check(itemId)) {
                throw new IllegalArgumentException("No such item");
            }
            if(quantity > 0 ) consignmentReal.setQuantity(quantity);
            consignmentReal.setDate(date);
            consignmentReal.setItemId(itemId);
            consignmentRepo.save(consignmentReal);
        }}

    public List<Consignment> findAll() {
        return consignmentRepo.findAll();
    }


    public void delete(Long id) throws IllegalArgumentException{
        final Optional<Consignment> maybeCons = consignmentRepo.findById(id);
        if(maybeCons.isEmpty()) throw new RuntimeException("No such consignment (Sorry)");
        consignmentRepo.delete(maybeCons.get());
    }



    public ItemDto getItemByConsignment (long id) throws IllegalArgumentException{
        final Optional<Consignment> consignment = consignmentRepo.findById(id);
        if(consignment.isEmpty()) throw  new RuntimeException("No such consignment");
        else{
            Long itemId = consignment.get().getItemId();
            final RestTemplate restTemplate = new RestTemplate();
            final HttpEntity<Long> itemRequest = new HttpEntity<>(itemId);
            final ResponseEntity<ItemDto> itemResponse = restTemplate
                    .exchange(itemsUrlAdress + "/dto/" + itemId,
                            HttpMethod.GET, itemRequest, ItemDto.class);

            if (itemResponse.getStatusCode() != HttpStatus.NOT_FOUND)
                return itemResponse.getBody();
            else
                throw new IllegalArgumentException("Item not found!");
        }
    }

}
