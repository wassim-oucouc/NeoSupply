    package org.example.neosupply.service.impl;

    import org.example.neosupply.dto.request.InventoryMovementDTO;
    import org.example.neosupply.dto.response.InventoryMovementDtoResponse;
    import org.example.neosupply.entity.InventoryMovement;
    import org.example.neosupply.exceptions.InventoryMovementNotFoundException;
    import org.example.neosupply.mapper.InventoryMovementMapper;
    import org.example.neosupply.repository.InventoryMovementRepository;
    import org.example.neosupply.service.InventoryMovementService;
    import org.example.neosupply.service.InventoryService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;


    @Service
    public class InventoryMovementServiceImpl implements InventoryMovementService {


        private final InventoryMovementRepository inventoryMovementRepository;
        private final InventoryMovementMapper inventoryMovementMapper;


        @Autowired
        public InventoryMovementServiceImpl(InventoryMovementRepository inventoryMovementRepository, InventoryMovementMapper inventoryMovementMapper)
        {
            this.inventoryMovementRepository = inventoryMovementRepository;
            this.inventoryMovementMapper = inventoryMovementMapper;
        }



        public InventoryMovementDtoResponse createInventoryMovement(InventoryMovementDTO inventoryMovementDTO)
        {
           InventoryMovement inventoryMovement = this.inventoryMovementMapper.toEntity(inventoryMovementDTO);

            this.inventoryMovementRepository.save(inventoryMovement);

           return  this.inventoryMovementMapper.toDtoResponse(inventoryMovement);
        }
        public InventoryMovementDtoResponse updateInventoryMovement(InventoryMovementDTO inventoryMovementDTO,Long id)
        {
            InventoryMovement inventoryMovement = this.inventoryMovementMapper.toEntity(inventoryMovementDTO);
            InventoryMovement inventoryMovementFound = this.inventoryMovementRepository.findInventoryMovementById(id).orElseThrow(() ->  new InventoryMovementNotFoundException("this inventory movement not found with id : " + id));
            inventoryMovementFound.setMovementDate(inventoryMovement.getMovementDate());
            inventoryMovementFound.setWarehouse(inventoryMovement.getWarehouse());
            inventoryMovementFound.setType(inventoryMovement.getType());
            inventoryMovementFound.setQuantity(inventoryMovement.getQuantity());
            return this.inventoryMovementMapper.toDtoResponse(this.inventoryMovementRepository.save(inventoryMovementFound));


        }
        public InventoryMovementDtoResponse findInventoryMovementById(Long id)
        {
            InventoryMovement inventoryMovementFound = this.inventoryMovementRepository.findInventoryMovementById(id).orElseThrow(() ->  new InventoryMovementNotFoundException("this inventory movement not found with id : " + id));
            return this.inventoryMovementMapper.toDtoResponse(inventoryMovementFound);
        }
    }
