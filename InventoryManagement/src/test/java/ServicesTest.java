import com.example.inventorymanagement.client.microservices.*;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.inventorymanagement.util.exceptions.UserExistenceException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.*;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ServicesTest implements ControllerInterface {



   private static Registry registry;
   private static ClientCallbackImpl clientCallback;
   @BeforeAll
   public static void setUp() {
      try {

         registry = LocateRegistry.getRegistry("localhost", 2018);

         User user = new User("testadmin", "admintest", "admin");
         clientCallback = new ClientCallbackImpl(user);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void fetchAndUpdate() throws RemoteException {

   }

   @Override
   public String getObjectsUsed() throws RemoteException {

      return "";
   }


   @Test
   public void testAddUserService() throws RemoteException, NotBoundException, NotLoggedInException, OutOfRoleException, UserExistenceException {

      User newUser = new User("testUser", "password123", "admin");

      AddUserService addUserService = new AddUserService();
      boolean result = AddUserService.process(registry, clientCallback, newUser);

      assertTrue(result);
   }

   @Test
   public void testChangePasswordService() throws RemoteException, NotBoundException, UserExistenceException, OutOfRoleException, NotLoggedInException {
      User userToChange = new User("testUser", "password123", "admin");
      String newPassword = "newPassword";

      boolean result = ChangePasswordService.process(registry, clientCallback, userToChange, newPassword);

      assertTrue(result);
   }


   @Test

   public void testChangeUserRole() throws  RemoteException, UserExistenceException, OutOfRoleException, NotLoggedInException{

      User userToChange = new User("testUser", "password123", "admin");
      String neRole = "sales";

      boolean result = ChangeUserRoleService.process(registry,clientCallback,userToChange,neRole);

      assertTrue(result);
   }

   @Test
   public void testCreateItemListingService() throws RemoteException, NotBoundException, NotLoggedInException, OutOfRoleException {
      Item item = new Item("TestItem", 2234, 100, new LinkedHashMap<>());

      boolean result = CreateItemListingService.process(registry, clientCallback, item);

      assertTrue(result);
   }


   @Test
   public void testCreatePurchaseOrderService() {
      ItemOrder purchaseOrder = new ItemOrder(223,new User("Marven","", ""),"2/20/23",new LinkedList<>());

      boolean result = false;
      try {
         result = CreatePurchaseOrderService.process(registry, clientCallback, purchaseOrder);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertTrue(result);
   }

   @Test
   public void testCreateSalesInvoiceService() {
      ItemOrder salesInvoice = new ItemOrder(223,new User("Marven","", ""),"2/20/23",new LinkedList<>());

      boolean result = false;
      try {
         result = CreateSalesInvoiceService.process(registry, clientCallback, salesInvoice);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertTrue(result);
   }

   @Test
   public void testFetchActiveUsersService() {
      LinkedList<User> activeUsers = null;
      try {
         activeUsers = FetchActiveUsersService.process(registry, clientCallback);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertNotNull(activeUsers);
   }

   @Test
   public void testFetchCostTodayService() {

      float costToday = 0.0f;
      try {
         costToday = FetchCostTodayService.process(registry, clientCallback);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertTrue(costToday >= 0.0f);
   }

   @Test
   public void testFetchListOfItemsService() {
      LinkedList<Item> listOfItems = null;
      try {
         listOfItems = FetchListOfItemsService.process(registry, clientCallback);
      } catch (NotLoggedInException e) {
         e.printStackTrace();
      }

      assertNotNull(listOfItems);
   }

   @Test
   public void testFetchMonthlyCostService() {
      LinkedHashMap<Integer, Float> monthlyCost = null;
      try {
         monthlyCost = FetchMonthlyCostService.process(registry, clientCallback);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertNotNull(monthlyCost);
   }

   @Test
   public void testFetchMonthlyRevenueService() {

      LinkedHashMap<Integer, Float> monthlyRevenue = null;
      try {
         monthlyRevenue = FetchMonthlyRevenueService.process(registry, clientCallback);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertNotNull(monthlyRevenue);
   }

   @Test
   public void testFetchRevenueTodayService() {

      float revenueToday = 0.0f;
      try {
         revenueToday = FetchRevenueTodayService.process(registry, clientCallback);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertTrue(revenueToday >= 0.0f);
   }

   @Test
   public void testFetchSalesInvoicesService() {
      LinkedList<ItemOrder> salesInvoices = null;
      try {
         salesInvoices = FetchSalesInvoicesService.process(registry, clientCallback);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }


      assertNotNull(salesInvoices);
   }

   @Test
   public void testRemoveItemListingService() {
      Item itemToRemove = new Item("TestItem", 2234, 100, new LinkedHashMap<>());

      boolean result = false;
      try {
         result = RemoveItemListingService.process(registry, clientCallback, itemToRemove);
      } catch (NotLoggedInException | OutOfRoleException e) {
         e.printStackTrace();
      }

      assertTrue(result);
   }

   @Test
   public void testFetchSuppliersService() {

      ArrayList<String> suppliers = null;
      try {
         suppliers = FetchSuppliersService.process(registry, clientCallback);
      } catch (NotLoggedInException e) {
         e.printStackTrace();
      }

      assertNotNull(suppliers);
   }

   @Test
   public void testRemoveUserService() {
      User userToRemove = new User("testUser", "password123", "admin");

      boolean result = false;
      try {
         result = RemoveUserService.process(registry, clientCallback, userToRemove);
      } catch (NotLoggedInException | OutOfRoleException | UserExistenceException e) {
         e.printStackTrace();
      }

      assertTrue(result);
   }
}
