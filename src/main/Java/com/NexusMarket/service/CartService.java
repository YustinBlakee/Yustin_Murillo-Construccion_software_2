package com.NexusMarket.service;

import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import com.NexusMarket.ordermanagement.Cart;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final Map<String, Cart> carts = new ConcurrentHashMap<>();
    public Cart create(String cartId,String buyerId){ if(cartId==null||cartId.isBlank()||buyerId==null||buyerId.isBlank()) throw new BadRequestException("Cart id and buyer id are required"); Cart c=new Cart(cartId,buyerId); if(carts.putIfAbsent(cartId,c)!=null) throw new ResourceConflictException("Cart already exists: "+cartId); return c; }
    public Cart getById(String id){ Cart c=carts.get(id); if(c==null) throw new ResourceNotFoundException("Cart not found: "+id); return c; }
    public List<Cart> getAll(){ return List.copyOf(carts.values()); }
    public Cart addItem(String cartId,String productId,int quantity){ if(productId==null||productId.isBlank()) throw new BadRequestException("Product id is required"); Cart c=getById(cartId); c.addItem(productId,quantity); return c; }
    public Cart removeItem(String cartId,String productId){ getById(cartId).removeItem(productId); return getById(cartId); }
    public Cart clear(String cartId){ Cart c=getById(cartId); c.clear(); return c; }
}
