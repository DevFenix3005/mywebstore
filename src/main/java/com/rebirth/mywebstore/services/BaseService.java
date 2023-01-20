package com.rebirth.mywebstore.services;

import java.io.Serializable;
import java.util.List;

public interface BaseService<K0 extends Serializable, C1, U2, D3> {

    D3 fetchById(K0 id);

    List<D3> fetchAll();

    D3 insert(C1 model);

    D3 update(K0 key, U2 model);

    void delete(K0 key);


}
