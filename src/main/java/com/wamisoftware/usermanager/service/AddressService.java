package com.wamisoftware.usermanager.service;

import com.wamisoftware.usermanager.model.Address;

import java.util.List;

public interface AddressService {

    void create(Address address);

    List<Address> getAll();

    Address get(long id);

    Address update(Address address, long id);

    boolean delete(long id);
}
