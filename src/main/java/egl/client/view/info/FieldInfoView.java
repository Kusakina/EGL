package egl.client.view.info;

import egl.client.model.core.DatabaseEntity;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class FieldInfoView<T extends DatabaseEntity, FieldType extends DatabaseEntity> implements EntityInfoView<T> {

    private final EntityInfoView<FieldType> fieldInfoView;
    private final Function<T, FieldType> fieldGetter;

    @Override
    public void initData(T entity, boolean isCreated) {
        var field = fieldGetter.apply(entity);
        fieldInfoView.initData(field, isCreated);
    }

    @Override
    public void validateData() {
        fieldInfoView.validateData();
    }

    @Override
    public void fillData(T entity) {
        var field = fieldGetter.apply(entity);
        fieldInfoView.fillData(field);
    }
}
