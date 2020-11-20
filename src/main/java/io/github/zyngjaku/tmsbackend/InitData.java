package io.github.zyngjaku.tmsbackend;

import io.github.zyngjaku.tmsbackend.dao.*;
import io.github.zyngjaku.tmsbackend.dao.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class InitData implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(InitData.class);

    private PasswordEncoder passwordEncoder;
    private CompanyRepo companyRepo;
    private UserRepo userRepo;
    private VehicleRepo vehicleRepo;
    private OrderRepo orderRepo;
    private TransshipmentTerminalRepo transshipmentTerminalRepo;

    @Autowired
    public InitData(PasswordEncoder passwordEncoder, CompanyRepo companyRepo, UserRepo userRepo, VehicleRepo vehicleRepo, OrderRepo orderRepo, TransshipmentTerminalRepo transshipmentTerminalRepo) {
        this.passwordEncoder = passwordEncoder;
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
        this.vehicleRepo = vehicleRepo;
        this.orderRepo = orderRepo;
        this.transshipmentTerminalRepo = transshipmentTerminalRepo;
    }

    public void run(ApplicationArguments args) throws ParseException {
        Company company = new Company("Raben Group", "Cholerzyn 422", "Liszki", "32060", "Poland");
        companyRepo.save(company);

        User owner = new User("owner@raben-group.com", passwordEncoder.encode("owner"), "Ellise", "Bender", company, User.Role.OWNER);
        userRepo.save(owner);

        User forwarder = new User("forwarder@raben-group.com", passwordEncoder.encode("forwarder"), "Nicholas", "Christie", company, User.Role.FORWARDER);
        userRepo.save(forwarder);

        User driver1 = new User("driver1@raben-group.com", passwordEncoder.encode("driver1"), "Charity", "Mahoney", company, User.Role.DRIVER);
        driver1.setLocalization(new Localization(50.041428f, 19.997385f, 120, 23));
        userRepo.save(driver1);

        User driver2 = new User("driver2@raben-group.com", passwordEncoder.encode("driver2"), "Drake", "Callahan", company, User.Role.DRIVER);
        driver2.setLocalization(new Localization(49.276989f, 17.502504f, 243, 98));
        userRepo.save(driver2);

        User driver3 = new User("driver3@raben-group.com", passwordEncoder.encode("driver3"), "Sanya", "Vasquez", company, User.Role.DRIVER);
        driver3.setLocalization(new Localization(56.128284f, 14.660003f, 16, 102));
        userRepo.save(driver3);

        Vehicle vehicle1 = new Vehicle("Scania S-series", "KR73212", company);
        vehicleRepo.save(vehicle1);

        Vehicle vehicle2 = new Vehicle("Scania S-series", "KR234A4", company);
        vehicleRepo.save(vehicle2);

        TransshipmentTerminal t11 = new TransshipmentTerminal(50.040232f, 20.019142f, "Załadunek w Krk", 3600L, TransshipmentTerminal.Type.LOADING, 1);
        TransshipmentTerminal t12 = new TransshipmentTerminal(50.291965f, 18.733184f, "Załadunek w Kato", 1800L, TransshipmentTerminal.Type.LOADING, 2);
        TransshipmentTerminal t13 = new TransshipmentTerminal(49.175181f, 16.627564f, "Rozładunek w Brnie", 7200L, TransshipmentTerminal.Type.UNLOADING, 3);
        transshipmentTerminalRepo.save(t11);
        transshipmentTerminalRepo.save(t12);
        transshipmentTerminalRepo.save(t13);

        List<TransshipmentTerminal> transshipmentTerminalsO1 = new LinkedList<>();
        transshipmentTerminalsO1.add(t11);
        transshipmentTerminalsO1.add(t12);
        transshipmentTerminalsO1.add(t13);

        Date minDateO1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/10/2020");
        Date maxDateO1 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/2020");

        Order order1 = new Order(company, minDateO1, maxDateO1, false, "Zamówienie dla DHL", transshipmentTerminalsO1);
        orderRepo.save(order1);

        TransshipmentTerminal t21 = new TransshipmentTerminal(48.225902f, 16.475195f, "Załadunek w Wiedniu", 9000L, TransshipmentTerminal.Type.LOADING, 1);
        TransshipmentTerminal t22 = new TransshipmentTerminal(50.022575f, 19.947298f, "Rozładunek w Krakowie", 72000L, TransshipmentTerminal.Type.UNLOADING, 2);
        transshipmentTerminalRepo.save(t21);
        transshipmentTerminalRepo.save(t22);

        List<TransshipmentTerminal> transshipmentTerminalsO2 = new LinkedList<>();
        transshipmentTerminalsO2.add(t21);
        transshipmentTerminalsO2.add(t22);

        Date minDateO2 = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2020");
        Date maxDateO2 = new SimpleDateFormat("dd/MM/yyyy").parse("28/10/2020");

        Order order2 = new Order(company, minDateO2, maxDateO2, false, "Zamówienie dla InPost", transshipmentTerminalsO2);
        orderRepo.save(order2);

        logger.info("[InitData] Mock data has been created!");
    }
}