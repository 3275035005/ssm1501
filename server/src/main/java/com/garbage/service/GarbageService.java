package com.garbage.service;

import com.garbage.entity.Garbage;

public interface GarbageService extends BaseService<Garbage>{

    void audit(Garbage data);
}
