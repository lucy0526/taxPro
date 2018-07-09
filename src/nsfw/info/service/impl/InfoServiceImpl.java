package nsfw.info.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import nsfw.info.dao.InfoDao;
import nsfw.info.entity.Info;
import nsfw.info.service.InfoService;

import org.springframework.stereotype.Service;

import core.service.impl.BaseServiceImpl;
@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {
	private InfoDao infoDao;
	
	@Resource
	public void setInfoDao(InfoDao infoDao) {
		super.setBaseDao(infoDao);
		this.infoDao = infoDao;
	}
}
