package cn.itsource.aigou.mq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class PayCancelConfirmMessage implements MessageCreator,Serializable{
	private static final long serialVersionUID = 6115725103343803076L;
	private Boolean success = true;
	private Byte oldBisType;
	private Long oldBisKey;
	private Byte newBisType;
	private Long newBisKey;
	private Integer money;
	@Override
	public Message createMessage(Session session) throws JMSException {
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setBoolean("success", success);
		mapMessage.setByte("oldBisType", oldBisType);
		mapMessage.setLong("oldBisKey", oldBisKey);
		mapMessage.setByte("newBisType", newBisType);
		if(newBisKey!=null){
			mapMessage.setLong("newBisKey", newBisKey);
		}
		if(money!=null){
			mapMessage.setInt("money", money);
		}
		return mapMessage;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Byte getOldBisType() {
		return oldBisType;
	}
	public void setOldBisType(Byte oldBisType) {
		this.oldBisType = oldBisType;
	}
	public Long getOldBisKey() {
		return oldBisKey;
	}
	public void setOldBisKey(Long oldBisKey) {
		this.oldBisKey = oldBisKey;
	}
	public Byte getNewBisType() {
		return newBisType;
	}
	public void setNewBisType(Byte newBisType) {
		this.newBisType = newBisType;
	}
	public Long getNewBisKey() {
		return newBisKey;
	}
	public void setNewBisKey(Long newBisKey) {
		this.newBisKey = newBisKey;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
}
