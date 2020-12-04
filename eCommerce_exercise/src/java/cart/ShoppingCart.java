package cart;

import entity.Product;
import java.util.*;

/**
 *
 * @author juanluis
 */
public class ShoppingCart {

    public List<ShoppingCartItem> items;
    int numberOfItems;

    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>. If
     * item of the provided Product already exists in shopping cart, the
     * quantity of that item is incremented.
     */
    public synchronized void addItem(Product product) {
        ShoppingCartItem item = new ShoppingCartItem(product);

        if (!items.contains(item)) {
            items.add(item);
        } else {
            item.incrementQuantity();
        }
        numberOfItems++;
    }

    /**
     * Updates the <code>ShoppingCartItem</code> of the provided
     * <code>Product</code> to the specified quantity. If '<code>0</code>' is
     * the given quantity, the cart item is removed from the cart.
     */
    public synchronized void update(Product product, String quantity) {
        for (ShoppingCartItem item : items) {
            if (item.getProduct().equals(product)) {
                numberOfItems += Integer.parseInt(quantity) - item.getQuantity();
                item.setQuantity(Integer.parseInt(quantity));
                break;
            }
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Returns the sum quantities for all items maintained in shopping cart list
     */
    public synchronized int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Returns the sum of the product price multiplied by the quantity for all
     * items in shopping cart list
     */
    public synchronized double getTotal() {
        double totalPrice = 0;
        for (ShoppingCartItem item : this.getItems()) {
            totalPrice += item.getTotal();
            System.out.println(totalPrice);
        }
        System.out.println(this.getNumberOfItems());
        System.out.println(totalPrice);
        return totalPrice;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
    }

}