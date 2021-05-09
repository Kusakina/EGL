package egl.client.service.model;

import egl.core.model.DescribedData;

import java.util.List;

public interface DescribedDataService<T extends DescribedData> {

    List<? extends T> findAll();
}
