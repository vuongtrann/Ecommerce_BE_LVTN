package com.spring.ecommerce.lvtn.model.Dao.Respone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import com.spring.ecommerce.lvtn.model.Entity.Collection;
import com.spring.ecommerce.lvtn.utils.enums.Status;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(value = {"parent", "children"})
public interface ProductProjection {
    String getId();  // Lấy ID của sản phẩm

    String getName();  // Lấy tên sản phẩm

    String getDescription();  // Lấy mô tả sản phẩm

    String getSlug();  // Lấy slug sản phẩm

    String getPrimaryImageURL();  // Lấy URL hình ảnh chính của sản phẩm

    List<String> getImagesURL();  // Lấy danh sách URL của các hình ảnh

    String getSku();  // Lấy mã SKU của sản phẩm

    int getQuantityAvailable();  // Lấy số lượng sản phẩm có sẵn

    int getSoldQuantity();  // Lấy số lượng sản phẩm đã bán

    BigDecimal getOriginalPrice();  // Lấy giá gốc của sản phẩm

    BigDecimal getSellingPrice();  // Lấy giá bán của sản phẩm

    BigDecimal getDiscountedPrice();  // Lấy giá giảm của sản phẩm

    int getNoOfView();  // Lấy số lượt xem của sản phẩm

    String getSellingType();  // Lấy kiểu bán hàng (ví dụ: bán lẻ, bán buôn)

    List<CategoryProjection> getCategories();  // Lấy danh sách các danh mục của sản phẩm

    List<Collection> getCollections();  // Lấy danh sách các bộ sưu tập của sản phẩm

    Boolean getHasVariants();  // Lấy thông tin về việc sản phẩm có các biến thể hay không

    Status getStatus();  // Lấy trạng thái của sản phẩm

    Instant getCreatedAt();

    Instant getUpdatedAt();

}
