package project.gladiators.web.viewModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductViewModel extends BaseViewModel{
    private ProductViewModel product;
    private BigDecimal price;
}
