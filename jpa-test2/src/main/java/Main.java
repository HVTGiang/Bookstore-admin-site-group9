import com.book.dao.*;
import com.book.entity.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException {
        /*CategoryEntity c=new CategoryEntity();
        c.setName("Văn phòng phẩm");
        if(CategoryDAO.InsertCategory(c)){
            System.out.println("Đã thêm loại " + c.getName());
        }
        else {
            System.out.println("Thêm không thành công");
        }*/

//        List<CategoryEntity> cList;
//        cList=CategoryDAO.findCategory("");
//        for (CategoryEntity c: cList
//             ) {
//            System.out.println(c.getName());
//        }

//        CategoryEntity c;
//        c=CategoryDAO.findCategory("");
//        System.out.println(c.getName());

//        CategoryEntity c=CategoryDAO.findCategory(1);
//        c.setName("Sách thiếu nhi");
//        if(CategoryDAO.UpdateCategory(c)){
//            System.out.println("Đã cập nhật loại " + c.getName());
//        }
//        else {
//            System.out.println("Cập nhật không thành công");
//        }

//        UserEntity user = new UserEntity();
//        user.setName("Lê Đình Duy");
//        user.setImage("");
//        user.setAddress("Quận 9, TP.HCM");
//        user.setEmail("duyld@gmail.com");
//        user.setIsRole(1);
//        user.setPassword("28112002");
//        user.setPhone("0123456789");
//        if (UserDAO.save(user) == true) {
//            System.out.println("Thêm thành công user " + user.getName());
//        } else {
//            System.out.println("Thêm không thành công");
//        }

//        List<CategoryEntity> cList;
//        cList= CategoryDAO.getAll();
//        for (CategoryEntity c: cList
//             ) {
//            System.out.println(c.getName());
//        }

//        List<UserEntity> cList;
//        cList=UserDAO.find("Duy");
//        for (UserEntity c: cList
//             ) {
//            System.out.println(c);
//        }

//        String action=null;
//        System.out.println(action);
//        if (action == null) {
//            action = "home";
//        }
//        PaymethodEntity paymethod=new PaymethodEntity();
//        paymethod.setName("Chuyển khoản qua ngân hàng");
//        PayDAO.save(paymethod);
//
//        List<PaymethodEntity> cList;
//        cList= PayDAO.getAll();
//        for (PaymethodEntity c: cList
//             ) {
//            System.out.println(c.getName());
//        }
//
//        DeliveryEntity delivery=new DeliveryEntity();
//        delivery.setName("Vận chuyển hỏa tốc - toàn quốc");
//        delivery.setShipFee(90000);
//        DeliveryDAO.save(delivery);
//
//        List<DeliveryEntity> cList;
//        cList= DeliveryDAO.getAll();
//        for (DeliveryEntity c: cList
//             ) {
//            System.out.println(c.getName());
//        }

//        OrderEntity order = new OrderEntity();
//        order.setIdUser(9);
//        order.setIdSeller(12);
//        order.setCreateTime(Date.valueOf(LocalDate.now()));
//        order.setPhone("0356435684");
//        order.setAddress("Rạch Giá, Kiên Giang");
//        order.setContactName("Trần Thị Quế");
//        order.setIdMethod(1);
//        order.setIdDelivery(3);
//        order.setReceiveDate(Order.estimateReceiveDate(order));
//        order.setTotalPay(590000);
//        order.setStatus(6);
//        OrderDAO.save(order);

//        List<OrderEntity> cList;
//        cList= OrderDAO.getAll();
//        for (OrderEntity c: cList
//             ) {
//            System.out.println(c.getContactName());
//        }
//        OrderitemEntity orderitem = new OrderitemEntity();
//        orderitem.setIdOrder(5);
//        orderitem.setIdProduct(1);
//        orderitem.setQuantity(2);
//        orderitem.setPrice(ProductDAO.findById(orderitem.getIdProduct()).getSalePrice() * orderitem.getQuantity());
//        OrderItemDAO.save(orderitem);

//        System.out.println(OrderDAO.find(1).getCreateTime());
//        System.out.println(OrderDAO.find(1).getIdDelivery());
//        System.out.println(Order.estimateReceiveDate(OrderDAO.find(1)));

        List<Orderitem> orderitemEntities = OrderItemDAO.getAll();
        System.out.println(OrderItemDAO.getAll().get(0).getIdProduct());

//        List<OrderEntity> orderList = OrderDAO.get5LastestOrder();
//        System.out.println(orderList.size());
    }
}
