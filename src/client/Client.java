package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import entities.Car;
import entities.CarType;
import entities.User;
import entities.UserType;
import interfaces.Gateway;

public class Client {

    private String email;
    private String password;
    private UserType userType;
    private CarType carType;
    private User client;
    private boolean logged = false;
    private boolean canInsert;

    private Scanner sc = new Scanner(System.in);

    public Client(boolean ci) {
        client = new User();
        canInsert = ci;
        initialize();
    }
    
    private static void config() {
        System.setProperty("java.security.police", "java.policy");
    }

    private void initialize() {
        config();

        try {

            Registry registry = LocateRegistry.getRegistry("localhost",50005);
            Gateway stub = (Gateway) registry.lookup("Gateway");

            if(canInsert) {
                insertOnDB(stub);
            }

            while(!logged) {
                waitForLogIn(stub);
            }

            while(logged) {
                if(client.getType() == UserType.CLIENT)
                    clientConnected(stub);
                else
                    clerkConnected(stub);
            }

        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
        catch(NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void waitForLogIn(Gateway stub) throws RemoteException{
        System.out.println("[1] para cadastro\n[2] para login");
        int option = sc.nextInt();

        sc.nextLine();
        switch(option) {

            case 1 -> {
                System.out.println("email:");
                email = sc.nextLine();
                
                System.out.println("senha:");
                password = sc.nextLine();
                
                System.out.println("[0] para cliente ou [1] para funcionário");
                if(sc.nextInt() == 0) {
                    userType = UserType.CLIENT;
                }
                else {
                    userType = UserType.CLERK;
                }

                logged = stub.signUp(new User(email, password, userType));
                client.setType(userType);

                if(logged && client.getType() == UserType.CLIENT)
                    System.out.println("Conectado como Cliente!");
                else
                    System.out.println("Conectado como Funcionário!");
            }

            case 2 -> {
                System.out.println("email:");
                email = sc.nextLine();

                System.out.println("senha:");
                password = sc.nextLine();

                client = stub.signIn(new User(email, password, userType));

                if(client != null) {
                    logged = true;
                }

                if(logged && client.getType() == UserType.CLIENT)
                    System.out.println("Conectado como Cliente!");
                else
                    System.out.println("Conectado como Funcionário!");
            }

        }
    }

    private void clientConnected(Gateway stub) throws RemoteException { 
        System.out.println("// ============================================");
        System.out.println("      ==== Conectado como Cliente ====");
        System.out.println("[1] comprar veículo\n[2] buscar veículo\n[3] listar todos\n" +
        "[4] listar econômicos\n[5] listar intermediários\n" +
        "[6] listar executivos\n[7] total de carros");
        int option = sc.nextInt();
        System.out.println("// ============================================");

        sc.nextLine();
        switch(option) {

            case 1 -> {
                System.out.println("Digite o renavam do carro que deseja comprar:");
                String renavam = sc.nextLine();
                if(stub.buyCar(renavam)) {
                    System.out.println("Veículo comprado com sucesso!");
                }
                else {
                    System.out.println("Não foi possível realizar sua compra ;-;");
                }
            }

            case 2 -> {
                System.out.println("Digite o renavam ou modelo do veículo desejado:");
                String str = sc.nextLine();
                Car response = stub.searchCar(str);
                if(response != null) {
                    showCar(response);
                }
                else {
                    System.out.println("Veículo não encontrado.");
                }
            }

            case 3 -> {
                List<Car> list = stub.listAll();
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 4 -> {
                List<Car> list = stub.listCategory(CarType.ECONOMIC);
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 5 -> {
                List<Car> list = stub.listCategory(CarType.INTERMEDIARY);
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 6 -> {
                List<Car> list = stub.listCategory(CarType.EXECUTIVE);
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 7 -> { System.out.println("O total é de: " + stub.showTotal() + " Veículos"); }

        }
    }

    private void clerkConnected(Gateway stub) throws RemoteException {

        System.out.println("// ============================================");
        System.out.println("      ==== Conectado como Funcionário ====");
        System.out.println("[1] adicionar veículo\n[2] atualizar veículo\n" +
        "[3] deletar veículo\n[4] buscar veículo\n[5] listar todos\n" +
        "[6] listar econômicos\n[7] listar intermediários\n" +
        "[8] listar executivos\n[9] total de carros");
        int option = sc.nextInt();
        System.out.println("// ============================================");

        sc.nextLine();
        switch(option) {

            case 1 -> {
                System.out.println("Modelo:");
                String model = sc.nextLine();

                System.out.println("Renavam:");
                String rena = sc.nextLine();

                System.out.println("Ano de Fabricação:");
                String year = sc.nextLine();

                System.out.println("Preço:");
                double price = sc.nextDouble();

                System.out.println("Selecione o tipo do carro:\n[1] econômico\n"+
                "[2] intermediário\n[3] executivo");
                int type = sc.nextInt();

                if(type == 1)
                    carType = CarType.ECONOMIC;
                else if(type == 2)
                    carType = CarType.INTERMEDIARY;
                else
                    carType = CarType.EXECUTIVE;
                
                if(stub.addCar(new Car(model, rena, year, price, carType))) {
                    System.out.println("Carro cadastrado com sucesso.");
                }
            }

            case 2 -> {
                System.out.println("Insira o mesmo renavam do veículo que deseja atualizar!!!");
                System.out.println("Renavam:");
                String rena = sc.nextLine();

                System.out.println("Modelo:");
                String model = sc.nextLine();

                System.out.println("Ano de Fabricação:");
                String year = sc.nextLine();

                System.out.println("Preço:");
                double price = sc.nextDouble();

                System.out.println("Selecione o tipo do carro:\n[1] econômico\n"+
                "[2] intermediário\n[3] executivo");
                int type = sc.nextInt();

                if(type == 1)
                    carType = CarType.ECONOMIC;
                else if(type == 2)
                    carType = CarType.INTERMEDIARY;
                else
                    carType = CarType.EXECUTIVE;
                
                if(stub.updateCar(new Car(model, rena, year, price, carType))) {
                    System.out.println("Carro atualizado com sucesso.");
                }
            }

            case 3 -> {
                System.out.println("Digite o renavam do veículo que deseja remover:");
                String renavam = sc.nextLine();

                if(stub.deleteCar(renavam)) {
                    System.out.println("Veículo deletado com sucesso.");
                }
                else {
                    System.out.println("Veículo não encontrado.");
                }
            }

            case 4 -> {
                System.out.println("Digite o renavam ou modelo do veículo desejado:");
                String str = sc.nextLine();
                Car response = stub.searchCar(str);
                if(response != null) {
                    showCar(response);
                }
                else {
                    System.out.println("Veículo não encontrado.");
                }
            }

            case 5 -> {
                List<Car> list = stub.listAll();
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 6 -> {
                List<Car> list = stub.listCategory(CarType.ECONOMIC);
                for(Car car : list) {
                    showCar(car);
                }
            }
            
            case 7 -> {
                List<Car> list = stub.listCategory(CarType.INTERMEDIARY);
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 8 -> {
                List<Car> list = stub.listCategory(CarType.EXECUTIVE);
                for(Car car : list) {
                    showCar(car);
                }
            }

            case 9 -> { System.out.println("O total é de: " + stub.showTotal() + " Veículos"); }

        }

    }

    private void showCar(Car car) {
        System.out.println("\n// ============================================");
        System.out.println("Modelo: " + car.getModel());
        System.out.println("Renavam: " + car.getRenavam());
        System.out.println("Ano de Fabricação: " + car.getYear());
        System.out.println("Preço: R$" + car.getPrice());
        System.out.println("Quantidade em estoque: " + car.getQuant());
        System.out.println("Tipo: " + car.getType());
    }

    private void insertOnDB(Gateway stub) throws RemoteException {
        stub.addCar(new Car("Fusca","1234567890","2003",30000,CarType.ECONOMIC));
        stub.addCar(new Car("Gol","2345678901","2005",25000,CarType.ECONOMIC));
        stub.addCar(new Car("Palio","3456789012","2007",28000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Civic","4567890123","2010",35000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Corolla","5678901234","2012",40000,CarType.EXECUTIVE));
        stub.addCar(new Car("Fiesta","6789012345","2015",27000,CarType.ECONOMIC));
        stub.addCar(new Car("Uno","7890123456","2018",23000,CarType.ECONOMIC));
        stub.addCar(new Car("Onix","8901234567","2020",32000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Ka","9012345678","2019",26000,CarType.ECONOMIC));
        stub.addCar(new Car("Sandero","0123456789","2017",29000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Celta","1357924680","2014",24000,CarType.ECONOMIC));
        stub.addCar(new Car("HB20","2468013579","2016",31000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Fit","3579246801","2013",33000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Renegade","4680135792","2018",38000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Tucson","5792468013","2019",42000,CarType.EXECUTIVE));
        stub.addCar(new Car("HR-V","6901357924","2017",36000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Fusion","9013579246","2016",45000,CarType.EXECUTIVE));
        stub.addCar(new Car("Siena","0246801357","2014",27000,CarType.ECONOMIC));
        stub.addCar(new Car("Prisma","1357924680","2015",30000,CarType.INTERMEDIARY));
        stub.addCar(new Car("Tracker","2468013579","2018",40000,CarType.INTERMEDIARY));

        stub.addUser(new User("sample@mail.com","senha1",UserType.CLIENT));
        stub.addUser(new User("sample2@mail.com","senha2",UserType.CLERK));
    }

    public static void main(String[] args) {
        new Client(true);
    }

}
