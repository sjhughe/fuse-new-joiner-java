package org.galatea.starter.domain;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinnhubSymbol {

  private String displaySymbol;
  private String description;
  private String type;
  private String figi;

}
