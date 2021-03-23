package com.wamisoftware.usermanager.service;

import com.wamisoftware.usermanager.exception.AddressNotFoundException;
import com.wamisoftware.usermanager.model.Address;
import com.wamisoftware.usermanager.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger("AddressLogger");

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public void create(Address address) {
        addressRepository.save(address);
        logger.info("Saved new address");
    }

    @Override
    @Transactional
    public List<Address> getAll() {
        List<Address> addresses = addressRepository.findAll();
        logger.info("Got all addresses");
        return addresses;
    }

    @Override
    @Transactional
    public Address get(long id) {
        logger.info("Got address with id: {}", id);
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    @Transactional
    public Address update(Address address, long id) {
        return addressRepository.findById(id)
                .map(a -> {
                    a = address;
                    a.setId(id);
                    logger.info("Replaced address with id: {}", id);
                    return addressRepository.save(a);
                })
                .orElseGet(() -> {
                    address.setId(id);
                    logger.info("Saved new address");
                    return addressRepository.save(address);
                });
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.findById(id)
                    .map(address -> {
                        addressRepository.delete(address);
                        logger.info("Deleted address with id: {}", id);
                        return true;
                    });
            return true;
        } else return false;
    }
}
