package nostr.event.query;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nostr.base.GenericTagQuery;
import nostr.event.Kind;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompositionQuery {

    private Kind kind;

    private List<GenericTagQuery> anyMatchList;

    private List<GenericTagQuery> allMatchList;

    public CompositionQuery(){
        anyMatchList = new ArrayList<>();
        allMatchList = new ArrayList<>();
    }

    public CompositionQuery(Kind kind, List<GenericTagQuery> anyMatchList, List<GenericTagQuery> allMatchList){
        this.kind = kind;
        this.anyMatchList = anyMatchList;
        this.allMatchList = allMatchList;
    }
}
