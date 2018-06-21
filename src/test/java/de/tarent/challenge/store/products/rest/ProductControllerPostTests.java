package de.tarent.challenge.store.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.web.servlet.ResultActions;

import de.tarent.challenge.exeptions.EanIsEmptyException;
import de.tarent.challenge.exeptions.NoEansException;
import de.tarent.challenge.exeptions.product.name.InvalidProductNameException;
import de.tarent.challenge.exeptions.product.price.PriceLowerZeroException;
import de.tarent.challenge.exeptions.product.sku.InvalidSkuException;
import de.tarent.challenge.exeptions.product.sku.ProductAllreadyInUseException;
import de.tarent.challenge.store.products.Product;
import de.tarent.challenge.store.products.ProductControllerTests;

public class ProductControllerPostTests extends ProductControllerTests {


}
