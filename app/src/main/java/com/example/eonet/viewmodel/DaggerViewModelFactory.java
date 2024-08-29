package com.example.eonet.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;


@Singleton
public class DaggerViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public DaggerViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Provider<ViewModel> creator = creators.get(modelClass);

        if (creator == null) {
            Optional<Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>>> entry = creators.entrySet().stream()
                    .filter(e -> modelClass.isAssignableFrom(e.getKey()))
                    .findFirst();

            if (entry.isPresent()) {
                creator = entry.get().getValue();
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class " + modelClass);
            }
        }

        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
