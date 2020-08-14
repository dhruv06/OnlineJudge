package com.finalyear.saas.model;

public class QuestionData {
	private String uname;
	private String cname;
	private String qname;
	private String qdesc;
	private String sinput;
	private String soutput;
	private String[] input;
	private String[] output;
	private String[] marks;
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	public String getQdesc() {
		return qdesc;
	}
	public void setQdesc(String qdesc) {
		this.qdesc = qdesc;
	}
	public String getSinput() {
		return sinput;
	}
	public void setSinput(String sinput) {
		this.sinput = sinput;
	}
	public String getSoutput() {
		return soutput;
	}
	public void setSoutput(String soutput) {
		this.soutput = soutput;
	}
	public String[] getInput() {
		return input;
	}
	public void setInput(String[] input) {
		this.input = input;
	}
	public String[] getOutput() {
		return output;
	}
	public void setOutput(String[] output) {
		this.output = output;
	}
	public String[] getMarks() {
		return marks;
	}
	public void setMarks(String[] marks) {
		this.marks = marks;
	}
}
