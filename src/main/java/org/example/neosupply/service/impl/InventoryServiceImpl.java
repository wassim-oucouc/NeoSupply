package org.example.neosupply.service.impl;

import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Inventory;
import org.example.neosupply.entity.Product;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.exceptions.WarehouseNotFoundException;
import org.example.neosupply.mapper.InventoryMapper;
import org.example.neosupply.repository.InventoryRepository;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.repository.WarehouseRepository;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;
   private final ProductRepository productRepository;
   private final WarehouseRepository warehouseRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository,InventoryMapper inventoryMapper)
    {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryMapper = inventoryMapper;
    }

    public InventoryDtoResponse createInventory(InventoryDTO inventoryDTO)
    {
        Inventory inventory = this.inventoryMapper.toEntity(inventoryDTO);
        this.inventoryRepository.save(inventory);
        return this.inventoryMapper.toDtoResponse(inventory);
    }

    public InventoryDtoResponse updateInventoryById(InventoryDTO inventoryDTO, Long id)
    {
        Inventory inventory = this.inventoryRepository.findById(id).orElseThrow(() ->  new InventoryNotFoudException("Inventory not exists"));

        inventory.setProduct(this.productRepository.findProductById(inventoryDTO.getProductId()));
        inventory.setWarehouse(this.warehouseRepository.findWarehouseById((inventoryDTO.getWarehouseId())).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not Found")));
        inventory.setQuantityReserved(inventoryDTO.getQuantityReserved());
        inventory.setQuantityOnHand(inventoryDTO.getQuantityOnHand());
        this.inventoryRepository.save(inventory);

        return this.inventoryMapper.toDtoResponse(inventory);
    }

    public void deleteInventoryById(Long id)
    {
        this.inventoryRepository.deleteById(id);
    }

    public InventoryDtoResponse findInventoryById(Long id)
    {
        Inventory inventory =  this.inventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoudException("Inventory not exists"));

        return  this.inventoryMapper.toDtoResponse(inventory);
    }

    public List<InventoryDtoResponse> getAllProducts()
    {
        return this.inventoryRepository.findAll().stream().map(inventoryMapper::toDtoResponse).toList();
    }
    public InventoryDtoResponse updateInventory(InventoryDTO inventoryDTO) {
        Inventory existing = inventoryRepository.findInventoryByProductIdAndWarehouseId(
                inventoryDTO.getProductId(), inventoryDTO.getWarehouseId()
        ).orElseThrow(() -> new RuntimeException("Inventory not found for productId: "
                + inventoryDTO.getProductId() + " and warehouseId: " + inventoryDTO.getWarehouseId()));

        existing.setQuantityReserved(inventoryDTO.getQuantityReserved());

        Inventory updated = inventoryRepository.save(existing);
        return inventoryMapper.toDtoResponse(updated);
    }

    public Integer getProductQuantityByWarehouse(Long productId,Long warehouseId)
    {
       return this.inventoryRepository.findQuantityByProductIdAndWarehouse_Id(productId,warehouseId);
    }
    public Optional<InventoryDtoResponse> findInventoryByProductIdAndWarehouseId(Long warehouseId, Long productId)
    {
        Inventory inventory = this.inventoryRepository.findInventoryByProduct_IdAndWarehouse_Id(productId,warehouseId);
       return Optional.ofNullable(this.inventoryMapper.toDtoResponse(inventory));
    }

    public InventoryDtoResponse getInventoryByProductId(Long productId)
    {
        Inventory inventory = this.inventoryRepository.findInventoryByProduct_Id(productId).orElseThrow(() -> new InventoryNotFoudException("not found inventry with id :" + productId));
      return this.inventoryMapper.toDtoResponse(inventory);

    }

    public void TransformFromQuantityReservedToQuantityHand(Long productId,Integer QuantityOnHand,Integer QuantityReserved)
    {
        this.inventoryRepository.updateQuantityOnHandAndQuantityReservedOrderByProduct_Id(QuantityOnHand,QuantityReserved,productId);
    }

    public Optional<Inventory> checkProductQuantityReservedByProductId(Long productId)
    {
        return this.inventoryRepository.findInventoryByProduct_Id(productId);




    }










}
