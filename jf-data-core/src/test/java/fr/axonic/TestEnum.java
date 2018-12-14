package fr.axonic;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 29/07/16.
 */
public enum TestEnum implements AEnumItem{
    A("a","A test", 0),B("b","B test", 1),C("c","C test", 2),D("d","D test", 3);

    private String code, path, label;
    private int index;

    TestEnum(String code, String label, int index) {
        this.code = code;
        this.path = "fr.test";
        this.label = label;
        this.index = index;
    }

    private void setCode(String code) {
        this.code = code;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getPath() {
        return path;
    }

	@Override
	public int getIndex() {
		return index;
	}

}
