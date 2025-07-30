package com.rdm.usecase.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Garage {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        private String address;

        @Column(nullable = false)
        private String telephone;

        @Column(nullable = false)
        private String email;

        @ElementCollection
        @CollectionTable(name = "garage_opening_times", joinColumns = @JoinColumn(name = "garage_id"))
        private List<OpeningTime> openingTimes = new ArrayList<>();
        @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<Vehicle> vehicles = new ArrayList<>();

}
