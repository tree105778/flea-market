package com.playdata.transactionservice.repository;

import com.playdata.transactionservice.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}
