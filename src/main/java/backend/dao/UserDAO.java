package backend.dao;

import backend.model.Address;
import backend.model.User;

import java.util.List;

public interface UserDAO {


    // user related operation
    User getByEmail(String email);
    User get(int id);

    boolean add(User user);

    // adding and updating a new address
    Address getAddress(int addressId);
    boolean addAddress(Address address);
    boolean updateAddress(Address address);
    Address getBillingAddress(int userId);
    List<Address> listShippingAddresses(int userId);
}
