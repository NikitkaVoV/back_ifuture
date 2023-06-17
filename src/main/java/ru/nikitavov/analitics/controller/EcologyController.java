package ru.nikitavov.analitics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EcologyController {

    @PostMapping("/calculate/ecology")
    public ResponseEntity<EcologyResult> calculateEcology(@RequestBody Building building) {
        // Получение коэффициентов из бекенда
        double publicOpinionCoefficient = building.getPublicOpinionCoefficient();
        double affordabilityCoefficient = building.getAffordabilityCoefficient();
        double roadTrafficCoefficient = building.getRoadTrafficCoefficient();
        double developmentPotentialCoefficient = building.getDevelopmentPotentialCoefficient();
        double waterConsumptionCoefficient = building.getWaterConsumptionCoefficient();
        double electricityConsumptionCoefficient = building.getElectricityConsumptionCoefficient();
        double connectivityCoefficient = building.getConnectivityCoefficient();

        // Выполнение анализа погрешностей и получение соответствующих коэффициентов
        double sizeErrorCoefficient = analyzeSizeError(building);
        double distanceFromStoresCoefficient = analyzeDistanceFromStores(building);
        double distanceFromCityCenterCoefficient = analyzeDistanceFromCityCenter(building);

        // Рассчитываем загрязнение экологии с использованием коэффициентов и результатов анализа
        double pollution = (publicOpinionCoefficient * affordabilityCoefficient)
                + (roadTrafficCoefficient * 0.1)
                + (developmentPotentialCoefficient)
                + (waterConsumptionCoefficient * 100)
                + (electricityConsumptionCoefficient)
                + (connectivityCoefficient)
                + sizeErrorCoefficient
                + distanceFromStoresCoefficient
                + distanceFromCityCenterCoefficient;

        // Создание объекта с результатами экологического расчета
        EcologyResult result = new EcologyResult();
        result.setBuildingId(building.getId());
        result.setPollution(pollution);

        return ResponseEntity.ok(result);
    }

    private double analyzeSizeError(Building building) {
        // Выполнение анализа погрешности на размер квартир в здании
        double sizeError = 0.0;
        List<Double> apartmentSizes = building.getApartmentSizes();
        if (apartmentSizes != null && !apartmentSizes.isEmpty()) {
            // Выполнение анализа погрешности на основе размеров квартир
            double sumSizes = 0.0;
            for (double size : apartmentSizes) {
                sumSizes += size;
            }
            double averageSize = sumSizes / apartmentSizes.size();
            double sizeErrorPercentage = (averageSize - building.getStandardSize()) / building.getStandardSize();
            sizeError = sizeErrorPercentage * building.getSizeErrorCoefficient();
        }

        return sizeError;
    }

    private double analyzeDistanceFromStores(Building building) {
        // Выполнение анализа отдаленности от магазинов
        double distanceFromStores = building.getDistanceFromStores();
        double distanceCoefficient = building.getDistanceCoefficient();

        return distanceFromStores * distanceCoefficient;
    }

    private double analyzeDistanceFromCityCenter(Building building) {
        // Выполнение анализа отдаленности от центра города
        double distanceFromCityCenter = building.getDistanceFromCityCenter();
        double distanceCoefficient = building.getDistanceCoefficient();

        return distanceFromCityCenter * distanceCoefficient;
    }


}
