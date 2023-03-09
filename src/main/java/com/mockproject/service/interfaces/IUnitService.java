package com.mockproject.service.interfaces;

import com.mockproject.entity.Unit;

import java.util.List;

public interface IUnitService {

    List<Unit> listBySessionId(long sid);
}
