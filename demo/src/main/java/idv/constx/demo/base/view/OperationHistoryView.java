package idv.constx.demo.base.view;

import idv.constx.demo.base.entity.AuditStatusEnum;
import idv.constx.demo.base.entity.OperationHistoryEntity;

import java.io.Serializable;

public class OperationHistoryView extends View implements Serializable {

	private static final long serialVersionUID = 8668118419124826070L;

	public OperationHistoryView(OperationHistoryEntity entity) {
		super();
		super.put("operattime", entity.getOperattime());
		super.put("target", entity.getTarget());
		super.put("note", entity.getNote());
		if (entity.getStatus() != null) {
			super.put("statusStr",AuditStatusEnum.getAuditStatusByInt(entity.getStatus()).getName());
		}
		if (entity.getOperator() != null) {
			super.put("operatorStr", entity.getOperator().getShowName());
		}
		super.putShortString("note");
	}

}
