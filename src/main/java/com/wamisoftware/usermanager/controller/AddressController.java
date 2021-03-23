package com.wamisoftware.usermanager.controller;

import com.wamisoftware.usermanager.model.Address;
import com.wamisoftware.usermanager.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody Address address) {
        addressService.create(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        final List<Address> addresses = addressService.getAll();
        return addresses != null && !addresses.isEmpty()
                ? new ResponseEntity<>(addresses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<Address> get(@PathVariable("id") long id) {
        final Address address = addressService.get(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Address> update(@RequestBody Address address, @PathVariable("id") long id) {
        final Address updatedAddress = addressService.update(address, id);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        final boolean deleted = addressService.delete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
