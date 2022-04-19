package structure

/**
 * JMA - 19/04/2022 01:18
 * Prolog is optional
 **/
class Document( val root:NestedNode) {
    private var prolog: Prolog = object: Prolog { }

    constructor(prolog: Prolog, root:NestedNode) : this(root){
        this.prolog = prolog
    }
}
