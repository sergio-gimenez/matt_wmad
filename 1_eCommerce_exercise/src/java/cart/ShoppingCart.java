package cart;

import entity.Product;
import java.util.*;

/**
 *
 * @author juanluis
 */
public class ShoppingCart {

    // A hashmap with
    //      key:   productID
    //      value: ShoppingCartItem
    HashMap<Integer, ShoppingCartItem> items;
    int numberOfItems;

    public ShoppingCart() {
        items = new HashMap();
        numberOfItems = 0;
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>. If
     * item of the provided Product already exists in shopping cart, the
     * quantity of that item is incremented.
     */
    public synchronized void addItem(Product product) {
        int productId = product.getId();
        if (items.containsKey(productId)) {
            items.get(productId).incrementQuantity();
        } else {
            items.put(product.getId(), new ShoppingCartItem(product));
        }
    }

    /**
     * Updates the <code>ShoppingCartItem</code> of the provided
     * <code>Product</code> to the specified quantity. If '<code>0</code>' is
     * the given quantity, the cart item is removed from the cart.
     */
    public synchronized void update(Product product, Integer quantity) {
        int productId = product.getId();

        items.get(productId).setQuantity(quantity);
        numberOfItems += quantity - items.get(productId).getQuantity();
    }

    public synchronized List<ShoppingCartItem> getItems() {
        List<ShoppingCartItem> items = new ArrayList();
        items.addAll(this.items.values());
        return items;
    }

    /**
     * Returns the sum quantities for all items maintained in shopping cart list
     */
    public synchronized int getNumberOfItems() {
        numberOfItems = 0;
        for (ShoppingCartItem item : getItems()) {
            numberOfItems += item.getQuantity();
        }

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
        }
        return  Math.round(totalPrice * 100)/100.0;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
    }

}
