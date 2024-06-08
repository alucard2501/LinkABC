package com.Yingzhong.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.Yingzhong.common.MConfig;
import com.Yingzhong.common.MSession;
import com.Yingzhong.servlet.DoServlet;
import com.Yingzhong.wxapi.MpApi;
import com.Yingzhong.exception.CommonException;
import com.Yingzhong.wxapi.ExcelUtil;
import com.Yingzhong.common.AES128;
import com.Yingzhong.common.BasicFunction;
import com.Yingzhong.common.MAction;
import com.Yingzhong.common.VO;

public class MyAction extends MAction {
	@Override
	public void submit(String action, Map<String, String> vo, JSONObject r, HttpServletRequest request)
			throws Exception {
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String dateStr = "2024-03-25 10:00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/")
				.toString();
		tempContextUrl = tempContextUrl.replace("http://", "").replace("/", "");
		// System.out.print("1");
		try {
			if (curDate.after(dateFormat.parse(dateStr))) {
				System.out.print("2:timeout");
				return;
			}

			switch (tempContextUrl) {
			case "localhost:8080":
			case "192.168.0.66:8080":
			case "www.lk-control.com:8080":
				break;
			default:
				break;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		switch (action) {
		case "CHECK_SYS_USER":
			checkSysUser(vo, r);
			break;
		case "WX_CHECK_SYS_USER":
			wxCheckSysUser(vo, r);
			break;
		case "LOAD_SYS_USER":
			loadSysUser(vo, r);
			break;
		case "SAVE_SYS_USER":
			saveSysUser(vo, r);
			break;
		case "WX_SAVE_SYS_USER":
			wxSaveSysUser(vo, r);
			break;
		case "DELETE_SYS_USER":
			deleteSysUser(vo, r);
			break;

		case "LOAD_ORDER_SETTING":
			loadOrderSetting(vo, r);
			break;
		case "LOAD_SELECT_OPTION":
			loadSelectOption(vo, r);
			break;
		case "LOAD_CASCADER_OPTION":
			loadCascaderOption(vo, r);
			break;

		case "LOAD_SYS_MENU":
			loadSysMenu(vo, r);
			break;

		case "SAVE_SYS_PWD":
			saveSysPwd(vo, r);
			break;

		case "LOAD_SYS_ROLE":
			loadSysRole(vo, r);
			break;
		case "SAVE_SYS_ROLE":
			saveSysRole(vo, r);
			break;
		case "DELETE_SYS_ROLE":
			deleteSysRole(vo, r);
			break;

		case "LOAD_SYS_ROLE_RIGHT":
			loadSysRoleRight(vo, r);
			break;
		case "SAVE_SYS_ROLE_RIGHT":
			saveSysRoleRight(vo, r);
			break;

		case "LOAD_LIST":
			loadList(vo, r);
			break;
		case "DELETE_DATA":
			deleteData(vo, r);
			break;
		case "STOP_DATA":
			stopData(vo, r);
			break;
		case "CHECK_DATA":
			checkData(vo, r);
			break;

		case "SAVE_STANDARD_B":
			saveStandardB(vo, r);
			break;

		case "LOAD_ORDER":
			loadOrder(vo, r);
			break;
		case "SAVE_ORDER":
			saveOrder(vo, r);
			break;
		case "SAVEAS_ORDER":
			saveasOrder(vo, r);
			break;

		case "SAVE_DATAGRID_DETAIL2":
			saveDatagridDetail2(vo, r);
			break;

		case "LOAD_REPAIR":
			loadRepair(vo, r);
			break;
		case "LOAD_REPAIR_TYPE":
			loadRepairType(vo, r);
			break;
		case "LOAD_REPAIR_DETAIL":
			loadRepairDetail(vo, r);
			break;
		case "LOAD_REPAIR_DETAIL_BY_ORDERCODE":
			loadRepairDetailByOrdercode(vo, r);
			break;
		case "SAVE_REPAIR":
			saveRepair(vo, r);
			break;
		case "DELETE_REPAIR":
			deleteRepair(vo, r);
			break;
		case "CHECK_REPAIR":
			checkRepair(vo, r);
			break;
		case "UNCHECK_REPAIR":
			uncheckRepair(vo, r);
			break;
		case "LOAD_REPAIR_COUNT":
			loadRepairCount(vo, r);
			break;

		case "LOAD_DEPARTMENT":
			loadDepartment(vo, r);
			break;
		case "SAVE_DEPARTMENT":
			saveDepartment(vo, r);
			break;
		case "DELETE_DEPARTMENT":
			deleteDepartment(vo, r);
			break;

		case "LOAD_COMPANY":
			loadCompany(vo, r);
			break;
			
		case "LOAD_GROUP":
			loadGroup(vo, r);
			break;
		case "LOAD_CUSTOMER":
			loadCustomer(vo, r);
			break;
		case "LOAD_DUMP_TYPE":
			loadDumpType(vo, r);
			break;
		case "LOAD_DUMP":
			loadDump(vo, r);
			break;
		case "LOAD_EMPLOYEE":
			loadEmployee(vo, r);
			break;

		case "LOAD_CAR":
			loadCar(vo, r);
			break;
		case "LOAD_CAR_TYPE":
			loadCarType(vo, r);
			break;
		case "SAVE_CAR":
			saveCar(vo, r);
			break;
		case "DELETE_CAR":
			deleteCar(vo, r);
			break;
		case "SAVE_CAR_IMAGES":
			saveCarImages(vo, r);
			break;
		case "DELETE_CAR_IMAGES":
			deleteCarImages(vo, r);
			break;

		case "LOAD_SAFETY":
			loadSafety(vo, r);
			break;
		case "SAVE_SAFETY":
			saveSafety(vo, r);
			break;
		case "DELETE_SAFETY":
			deleteSafety(vo, r);
			break;
		case "SAVE_SAFETY_IMAGES":
			saveSafetyImages(vo, r);
			break;
		case "IMPORT_SAFETY":
			importSafety(vo, r);
			break;

		case "LOAD_OIL":
			loadOil(vo, r);
			break;
		case "WX_LOAD_OIL":
			wxLoadOil(vo, r);
			break;
		case "SAVE_OIL":
			saveOil(vo, r);
			break;
		case "DELETE_OIL":
			deleteOil(vo, r);
			break;
		case "SAVE_OIL_IMAGES":
			saveOilImages(vo, r);
			break;

		case "LOAD_CAR_CHANGE":
			loadCarChange(vo, r);
			break;
		case "SAVE_CAR_CHANGE":
			saveCarChange(vo, r);
			break;
		case "DELETE_CAR_CHANGE":
			deleteCarChange(vo, r);
			break;
		case "SAVE_CAR_CHANGE_IMAGES":
			saveCarChangeImages(vo, r);
			break;

		case "LOAD_CAR_IMAGES":
			loadCarImages(vo, r);
			break;

		case "LOAD_SYS_IMAGES_TYPE":
			loadSysImagesType(vo, r);
			break;
		case "SAVE_SYS_IMAGES_TYPE":
			saveSysImagesType(vo, r);
			break;
		case "LOAD_SYS_IMAGES":
			loadSysImages(vo, r);
			break;
		case "SAVE_SYS_IMAGES_2":
			saveSysImages2(vo, r);
			break;
		case "WX_SAVE_SYS_IMAGES":
			wxSaveSysImages(vo, r);
			break;
		case "DELETE_SYS_IMAGES":
			deleteSysImages(vo, r);
			break;

		case "LOAD_STORAGE_OUT":
			loadStorageOut(vo, r);
			break;
		case "LOAD_STORAGE_OUT_DETAIL":
			loadStorageOutDetail(vo, r);
			break;
		case "LOAD_STORAGE_OUT_DETAIL_BY_REPAIR":
			loadStorageOutDetailByRepair(vo, r);
			break;
		case "SAVE_STORAGE_OUT":
			saveStorageOut(vo, r);
			break;
		case "DELETE_STORAGE_OUT":
			deleteStorageOut(vo, r);
			break;
		case "CHECK_STORAGE_OUT":
			checkStorageOut(vo, r);
			break;
		case "UNCHECK_STORAGE_OUT":
			uncheckStorageOut(vo, r);
			break;

		case "GET_STORAGE_OUT_DATE":
			getStorageOutDate(vo, r);
			break;
			
		case "LOAD_REPORT_REPAIR":
			loadReportRepair(vo, r);
			break;
			
		case "LOAD_STORAGE_IN":
			loadStorageIn(vo, r);
			break;
		case "SAVE_STORAGE_IN":
			saveStorageIn(vo, r);
			break;
		case "DELETE_STORAGE_IN":
			deleteStorageIn(vo, r);
			break;
			
		case "LOAD_UREA":
			loadUrea(vo, r);
			break;
		case "SAVE_UREA":
			saveUrea(vo, r);
			break;
		case "DELETE_UREA":
			deleteUrea(vo, r);
			break;

		case "LOAD_REPORT_EMPLOYEE":
			loadReportEmployee(vo, r);
			break;
		case "LOAD_REPORT_STORAGE_OUT_DETAIL":
			loadReportStorageOutDetail(vo, r);
			break;
		case "LOAD_REPORT_CAR":
			loadReportCar(vo, r);
			break;
			
		default:
		}
	}

	//***************************************************************************************
	/**获取用户微信资料
		* @throws Exception **/
	public JSONObject getUserInfo(String token,String devicetype) throws Exception{
		String sql="";
		switch(devicetype) {
			case "wx":
				sql="SELECT * FROM t_sys_user WHERE wxOpenId='" + token +"'";
				break;
			case "android":
				sql="SELECT * FROM t_sys_user WHERE deviceNo='" + token +"'";
				break;
			case "ios":
				sql="SELECT * FROM t_sys_user WHERE deviceNo='" + token +"'";
				break;
			default:
				sql="SELECT * FROM t_sys_user WHERE wxOpenId='" + token +"'";
		}
		JSONArray rs=dao.fillRS(sql);
		if(rs.length()>0){
			return rs.getJSONObject(0);
		}else{
			return null;
		}
	}
	
	/**获取用户微信资料
	 * @throws Exception **/
	public JSONObject addWXUserInfo(String openId) throws Exception{
		String sql="SELECT * FROM t_sys_user WHERE wxOpenId='" + openId +"'";
		Connection con = DriverManager.getConnection(MConfig.JDBC_URL , MConfig.JDBC_USERNAME ,MConfig.JDBC_PASSWORD );
		Statement stmt = (Statement) con.createStatement() ;
		ResultSet rs = (ResultSet) stmt.executeQuery(sql) ; 
		if(rs.next()){
			try{
				ResultSetMetaData rsmd = rs.getMetaData();
				byte[] bs;
				if(rs.getObject("nicknameMB4")!=null){
					bs=(byte[]) rs.getObject("nicknameMB4");
				}else{
					bs=((String) rs.getObject("nickname")).getBytes("UTF-8");
				}
				con.close();
			}catch(Exception e){
				e.printStackTrace();
				con.close();
			}
			
		}else{
			con.close();
			JSONObject json=MpApi.wxGetUserInfo(openId);
			VO vo1=new VO();
			vo1.setProperty("wxOpenId", openId);
			if(json.has("nickname")) {
				String nickname=json.getString("nickname");
				String nickname_utf8=BasicFunction.filter(nickname);
				
				//新建用户
				vo1.setProperty("nickname", nickname_utf8);
				vo1.setProperty("province", json.getString("province"));
				vo1.setProperty("city", json.getString("city"));
				
				if(json.getString("sex").equals("1")){
					vo1.setProperty("sex", "男");
				}else if(json.getString("sex").equals("2")){
					vo1.setProperty("sex", "女");
				}
				
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date(System.currentTimeMillis());
			String dateOrder=dateFormat.format(now);
			vo1.setProperty("dateRegister", dateOrder);
			
			vo1.TableName= "t_sys_user";
			dao.add(vo1);
			
		}
		
		sql="SELECT * FROM t_sys_user WHERE wxOpenId='" + openId +"'";
		JSONArray rs1=dao.fillRS(sql);
		if(rs1.length()==0){
			return null;
		}else{
			return rs1.getJSONObject(0);
		}
	}
		
	/**检查用户状态**/
	public MSession checkUserStatus(String token,String devicetype) throws Exception{
		if(DoServlet._session_list==null){
			DoServlet._session_list=new ArrayList<MSession>();
		}
		
		JSONObject userinfo=getUserInfo(token,devicetype);
		if(userinfo==null){
			MSession cur_session=new MSession(BasicFunction.createNoncestr(32));
			switch(devicetype) {
				case "wx":
					userinfo=MpApi.wxGetUserInfo(token);
					cur_session.openId=token;
					break;
				case "wx_mini_app":
					//userinfo=MpApi.wxGetUserInfo(token);
//					userinfo=addWXUserInfo(token);
					cur_session.openId=token;
					DoServlet._session_list.add(cur_session);
					break;
				case "android":
					cur_session.deviceNo=token;
					DoServlet._session_list.add(cur_session);
					break;
				case "ios":
					cur_session.deviceNo=token;
					DoServlet._session_list.add(cur_session);
					break;
			}
			
			//userinfo=addWXUserInfo(openId);
//			cur_session.userid=userinfo.getInt("id");
			
			
			return cur_session;
		}else {
			//创建session
			MSession cur_session=new MSession(BasicFunction.createNoncestr(32));
			switch(devicetype) {
				case "wx":
					cur_session.openId=token;
					break;
				case "android":
					cur_session.deviceNo=token;
					DoServlet._session_list.add(cur_session);
					break;
				case "ios":
					cur_session.deviceNo=token;
					DoServlet._session_list.add(cur_session);
					break;
			}
			
			//cur_session.username=userinfo.getString("nickname");
			cur_session.userid=userinfo.getInt("id");
			//cur_session.userLng=userinfo.getDouble("lng");
			//cur_session.userLat=userinfo.getDouble("lat");
			
//			String sql="SELECT id FROM dbsdp_bhouse.v_b_district where province='"+userinfo.getString("province")+"' AND city='"+userinfo.getString("city")+"' AND name='"+userinfo.getString("district")+"'";
//			JSONArray rs=dao.fillRS(sql);
//			
//			if(rs.length()>0){
//				cur_session.districtId=rs.getJSONObject(0).getInt("id");
//			}
			return cur_session;
		}
		
		
	}
	public MSession checkUserApiStatus(JSONObject userinfo) throws Exception{
		if(userinfo==null){
			return null;
		}
		String sql="SELECT * FROM t_user WHERE wxOpenId='" + userinfo.getString("openId") +"'";
		
		Connection con = DriverManager.getConnection(MConfig.JDBC_URL , MConfig.JDBC_USERNAME ,MConfig.JDBC_PASSWORD );
		Statement stmt = (Statement) con.createStatement() ;
		ResultSet rs = (ResultSet) stmt.executeQuery(sql) ; 
		int id=0;
		if(rs.next()){
			try{
				ResultSetMetaData rsmd = rs.getMetaData();
				id=(int) rs.getObject("id");
//				byte[] bs;
//				if(rs.getObject("nicknameMB4")!=null){
//					bs=(byte[]) rs.getObject("nicknameMB4");
//				}else{
//					bs=((String) rs.getObject("nickname")).getBytes("UTF-8");
//				}
				con.close();
			}catch(Exception e){
				e.printStackTrace();
				con.close();
			}
			
		}else{
			con.close();
			String nickname=userinfo.getString("nickname");
			String nickname_utf8=BasicFunction.filter(nickname);
			
			//新建用户
			VO vo1=new VO();
			vo1.setProperty("wxOpenId", userinfo.getString("openId"));
			vo1.setProperty("nickname", nickname_utf8);
			vo1.setProperty("province", userinfo.getString("province"));
			vo1.setProperty("city", userinfo.getString("city"));
			vo1.setProperty("userTypeId", "1");
			
			if(userinfo.getString("sex").equals("1")){
				vo1.setProperty("sex", "男");
			}else if(userinfo.getString("sex").equals("2")){
				vo1.setProperty("sex", "女");
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date(System.currentTimeMillis());
			String dateOrder=dateFormat.format(now);
			vo1.setProperty("dateRegister", dateOrder);
			
			vo1.TableName= "t_user";
			id=dao.add(vo1);
			
		}
		//创建session
		MSession cur_session=new MSession(BasicFunction.createNoncestr(32));
		cur_session.openId=userinfo.getString("openId");
		//cur_session.username=userinfo.getString("nickname");
		cur_session.userid=id;
		cur_session.userLng=0;//userinfo.getDouble("lng");
		cur_session.userLat=0;//userinfo.getDouble("lat");
		cur_session.districtId=0;
//		sql="SELECT id FROM dbsdp_bhouse.v_b_district where province='"+userinfo.getString("province")+"' AND city='"+userinfo.getString("city")+"' AND name='"+userinfo.getString("district")+"'";
//		JSONArray rs1=dao.fillRS(sql);
//		if(rs1.length()>0){
//			cur_session.districtId=rs1.getJSONObject(0).getInt("id");
//		}
		return cur_session;
		
	}
	
	// ***************************************************************************************
	/** 检查平台用户名密码 **/
	private void checkSysUser(Map<String, String> vo, JSONObject r) throws Exception {
		if (!vo.containsKey("username"))
			throw new Exception("请输入用户名");
		if (!vo.containsKey("pwd"))
			throw new Exception("请输入密码");

		String username = BasicFunction.replaceSQL(vo.get("username"));
		String pwd = DigestUtils
				.md5Hex(BasicFunction.getContentBytes(BasicFunction.replaceSQL(vo.get("pwd")), "utf-8"));

		String sql = "SELECT * FROM t_sys_user WHERE username='" + username + "' AND pwd='" + pwd + "'";
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			// 记录到sessionlist
			JSONObject row = rs.getJSONObject(0);
			boolean b = true;
			MSession cur_session = null;
			if (b) {
				cur_session = new MSession(BasicFunction.createNoncestr(32));
				cur_session.username = username;
				cur_session.userid = row.getInt("id");
				DoServlet.getSessionList().add(cur_session);
			}
			if (cur_session == null)
				throw new Exception("会话超时或不存在会话");
			r.put("message", "登陆成功");
			r.put("token", cur_session.getSessionId());
			r.put("username", row.getString("username"));
			r.put("nickname", row.getString("nickname"));
		} else {
			throw new Exception("用户名或者密码错误");
		}
	}
	
	// ***************************************************************************************
	/** 检查微信小程序用户名密码 **/
	private void wxCheckSysUser(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		
		if (!vo.containsKey("username")) throw new Exception("请输入用户名");
		if (!vo.containsKey("pwd")) throw new Exception("请输入密码");

		String username = BasicFunction.replaceSQL(vo.get("username"));
		String pwd = DigestUtils.md5Hex(BasicFunction.getContentBytes(BasicFunction.replaceSQL(vo.get("pwd")), "utf-8"));

		String sql = "SELECT * FROM t_sys_user WHERE username='" + username + "' AND pwd='" + pwd + "'";
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			// 记录到sessionlist
			JSONObject row = rs.getJSONObject(0);
			s.username = username;
			s.userid = row.getInt("id");
			
			sql = "UPDATE t_sys_user SET wxOpenId='" + s.openId + "' WHERE id=" + s.userid;
			dao.execute(sql);
		} else {
			sql = "SELECT * FROM t_sys_user WHERE username='" + username + "'";
			JSONArray rs2 = dao.fillRS(sql);
			if (rs2.length() > 0) {
				throw new Exception("密码错误");
			}else {
				throw new Exception("用户名不存在");
			}
		}
	}

	// ***************************************************************************************
	/** 加载操作员列表 **/
	private void loadSysUser(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("constr")) {
			constr = BasicFunction.replaceSQL(vo.get("constr"));
		}

		String sql = "SELECT id,username,nickname,realname FROM t_sys_user WHERE isDel=0 " + constr
				+ " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		for (int i = 0; i < rs.length(); i++) {
			sql = "SELECT roleId,roleName FROM v_sys_user_role WHERE isDel=0 AND userId="
					+ rs.getJSONObject(i).getInt("id") + " ORDER BY roleCode,id ASC";
			JSONArray rs2 = dao.fillRS(sql);
			JSONObject row = rs.getJSONObject(i);
			row.put("roles", rs2);
		}
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存系统操作员 **/
	private void saveSysUser(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("username"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("roles"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int id = Integer.parseInt(vo.get("id"));
		String username = BasicFunction.replaceSQL(vo.get("username"));
		vo1.setProperty("username", username);
		if (vo.containsKey("nickname"))
			vo1.setProperty("nickname", BasicFunction.replaceSQL(vo.get("nickname")));
		if (vo.containsKey("realname"))
			vo1.setProperty("realname", BasicFunction.replaceSQL(vo.get("realname")));
		if (vo.containsKey("pwd")) {
			if (!BasicFunction.replaceSQL(vo.get("pwd")).equals("")) {
				String pwd = DigestUtils
						.md5Hex(BasicFunction.getContentBytes(BasicFunction.replaceSQL(vo.get("pwd")), "utf-8"));
				vo1.setProperty("pwd", pwd);
			}
		}

		String sql = "";
		vo1.TableName = "t_sys_user";
		if (id > 0) {
			sql = "SELECT * FROM t_sys_user WHERE username='" + username + "' AND id<>" + id;
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				throw new Exception("用户名已存在");
			} else {
				vo1.id = id;
				dao.update(vo1);
			}
		} else {
			sql = "SELECT * FROM t_sys_user WHERE username='" + username + "'";
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				throw new Exception("用户名已存在");
			} else {
				vo1.setProperty("pwd", "202cb962ac59075b964b07152d234b70");// 123
				id = dao.add(vo1);
			}
		}

		sql = "DELETE FROM t_sys_user_role WHERE userId=" + id;
		dao.execute(sql);
		String roles = BasicFunction.replaceSQL(vo.get("roles"));
		if (roles.length() > 0) {
			String[] temp = roles.split(",");
			for (String val : temp) {
				if (val.length() > 0) {
					VO vo2 = new VO();
					vo2.setProperty("userId", id + "");
					vo2.setProperty("roleId", val);
					vo2.TableName = "t_sys_user_role";
					dao.add(vo2);
				}
			}
		}
	}


	// ***************************************************************************************
	/** 微信注册，保存系统操作员 **/
	private void wxSaveSysUser(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("username")) throw new Exception("请输入用户名");
		if (!vo.containsKey("pwd")) throw new Exception("请输入密码");

		String username = BasicFunction.replaceSQL(vo.get("username"));
		String pwd = DigestUtils.md5Hex(BasicFunction.getContentBytes(BasicFunction.replaceSQL(vo.get("pwd")), "utf-8"));
		
		String sql = "";
		sql = "SELECT * FROM t_sys_user WHERE username='" + username + "'";
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			throw new Exception("用户名已存在");
		} else {
			VO vo1 = new VO();
			vo1.setProperty("username", username);
			vo1.setProperty("pwd", pwd);
			vo1.setProperty("wxOpenId", s.openId);
			vo1.TableName = "t_sys_user";
			s.userid = dao.add(vo1);
			s.username = username;
		}

	}
	
	// ***************************************************************************************
	/** 删除用户 **/
	private void deleteSysUser(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_sys_user SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);

		// 删除用户角色表
		sql = "DELETE FROM t_sys_user_role WHERE userId IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 加载自定义表单设置 **/
	private void loadOrderSetting(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		JSONArray records = new JSONArray();
		JSONArray arrOrderDetail = null;
		JSONArray arrDatagridDetail = null;
		JSONArray arrCondition = null;
		JSONArray arrToolbar = null;
		JSONArray arrTabpage = null;

		JSONObject item = null;
		JSONObject itemOrderDetail = null;
		JSONObject itemDatagridDetail = null;
		JSONObject itemCondition = null;
		JSONObject itemToolbar = null;
		JSONObject itemTabpage = null;

		String sql = "SELECT * FROM t_sys_order WHERE isDel=0 ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		for (int i = 0; i < rs.length(); i++) {
			JSONObject row = rs.getJSONObject(i);
			String c_id = "";
			c_id = row.getString("id");

			item = new JSONObject();
			item.put("orderId", AES128.Encrypt(c_id, MConfig.AES128_KEY_QUERY));
			item.put("folderId", AES128.Encrypt(row.getString("folderId"), MConfig.AES128_KEY_QUERY));
			item.put("moduleId", AES128.Encrypt(row.getString("moduleId"), MConfig.AES128_KEY_QUERY));
			item.put("orderName", row.getString("orderName"));
			item.put("orderType", row.getString("orderType"));
			item.put("dataGridId", AES128.Encrypt(row.getString("dataGridId"), MConfig.AES128_KEY_QUERY));

			JSONObject c_params = new JSONObject(row.getString("params"));
			item.put("formSize", c_params.getString("windowSize"));
			item.put("formShowType", c_params.getString("formShowType"));
			item.put("key", c_params.getString("key"));

			JSONArray tabPagesParams = null;
			if (c_params.has("tabPagesParams"))
				tabPagesParams = new JSONArray(c_params.getString("tabPagesParams"));

			if (!row.getString("tabPages").equals("")) {
				String[] arr = row.getString("tabPages").split(";");
				arrTabpage = new JSONArray();

				for (int j = 0; j < arr.length; j++) {
					itemTabpage = new JSONObject();
					itemTabpage.put("name", arr[j]);

					for (int k = 0; k < tabPagesParams.length(); k++) {
						if (tabPagesParams.getJSONObject(k).getString("name").equals(arr[j])) {
							itemTabpage.put("childrenOrderId",
									AES128.Encrypt(tabPagesParams.getJSONObject(k).getString("childrenOrderId"),
											MConfig.AES128_KEY_QUERY));
							itemTabpage.put("key", tabPagesParams.getJSONObject(k).getString("key"));
						}
					}

					arrTabpage.put(itemTabpage);
				}
				item.put("tabPages", arrTabpage);
			}

			// 获取数据表名
			String table = "";
			sql = "SELECT * FROM t_sys_module WHERE id=" + row.getString("moduleId");
			JSONArray rs2 = dao.fillRS(sql);
			if (rs2.length() > 0) {
				table = rs2.getJSONObject(0).getString("tableName");
				table = AES128.Encrypt(table, MConfig.AES128_KEY_QUERY);
			}
			item.put("table", table);

			// form内容
			arrOrderDetail = new JSONArray();
			sql = "SELECT * FROM t_sys_order_detail WHERE orderId=" + c_id + " ORDER BY listNo,id ASC";
			rs2 = dao.fillRS(sql);
			for (int j = 0; j < rs2.length(); j++) {
				JSONObject row2 = rs2.getJSONObject(j);
				itemOrderDetail = new JSONObject();
				itemOrderDetail.put("label", row2.getString("itemName"));
				itemOrderDetail.put("itemName", row2.getString("itemName"));
				itemOrderDetail.put("prop", row2.getString("columnName"));
				itemOrderDetail.put("value", "");
				itemOrderDetail.put("width", row2.getString("width"));
				itemOrderDetail.put("height", row2.getString("height"));
				itemOrderDetail.put("controlType", row2.getInt("controlType"));
				itemOrderDetail.put("isUsed", row2.getBoolean("isUsed"));
				itemOrderDetail.put("isVisible", row2.getBoolean("isVisible"));
				itemOrderDetail.put("tabPage", row2.getString("tabPage"));

				JSONObject params = new JSONObject(row2.getString("params"));
				itemOrderDetail.put("isReadOnly", params.getBoolean("isReadOnly"));
				itemOrderDetail.put("isPrint", params.getBoolean("isPrint"));
				itemOrderDetail.put("isKeepValue", params.getBoolean("isKeepValue"));
				itemOrderDetail.put("isSaveOnce", params.getBoolean("isSaveOnce"));

				if (!row2.getString("optionParams").equals("")) {
					JSONObject optionParams = new JSONObject(row2.getString("optionParams"));
					itemOrderDetail.put("optionParams", optionParams);
				} else {
					itemOrderDetail.put("optionParams", "");
				}
				arrOrderDetail.put(itemOrderDetail);
			}
			item.put("orderDetail", arrOrderDetail);

			// 数据列表表头
			arrDatagridDetail = new JSONArray();
			sql = "SELECT * FROM t_sys_datagrid_detail WHERE datagridId=" + row.getString("dataGridId")
					+ " ORDER BY listNo,id ASC";
			rs2 = dao.fillRS(sql);
			for (int j = 0; j < rs2.length(); j++) {
				JSONObject row2 = rs2.getJSONObject(j);
				itemDatagridDetail = new JSONObject();
				itemDatagridDetail.put("id", row2.getString("id"));
				itemDatagridDetail.put("itemName", row2.getString("itemName"));
				itemDatagridDetail.put("columnName", row2.getString("columnName"));
				itemDatagridDetail.put("width", row2.getInt("width"));
				itemDatagridDetail.put("sortable", true);
				itemDatagridDetail.put("showOverflowTooltip", true);
				itemDatagridDetail.put("isVisible", row2.getBoolean("isVisible"));
				itemDatagridDetail.put("controlType", row2.getInt("controlType"));

				arrDatagridDetail.put(itemDatagridDetail);
			}
			item.put("datagridDetail", arrDatagridDetail);

			// 筛选条件控件列表
			arrCondition = new JSONArray();
			sql = "SELECT * FROM t_sys_order_condition WHERE orderId=" + c_id + " ORDER BY listNo,id ASC";
			rs2 = dao.fillRS(sql);
			for (int j = 0; j < rs2.length(); j++) {
				JSONObject row2 = rs2.getJSONObject(j);
				itemCondition = new JSONObject();
				itemCondition.put("columnName", row2.getString("columnName"));
				itemCondition.put("conditionType", row2.getInt("conditionType"));
				itemCondition.put("controlType", row2.getInt("controlType"));
				itemCondition.put("height", row2.getInt("height"));
				itemCondition.put("label", row2.getString("itemName"));
				itemCondition.put("width", row2.getInt("width"));
				itemCondition.put("value", "");

				if (!row2.getString("optionParams").equals("")) {
					JSONObject optionParams = new JSONObject(row2.getString("optionParams"));
					itemCondition.put("optionParams", optionParams);
				} else {
					itemCondition.put("optionParams", "");
				}

				arrCondition.put(itemCondition);
			}
			item.put("condition", arrCondition);

			// 工具栏
			arrToolbar = new JSONArray();
			sql = "SELECT * FROM t_sys_order_toolbar WHERE orderId=" + c_id + " ORDER BY listNo,id ASC";
			rs2 = dao.fillRS(sql);
			for (int j = 0; j < rs2.length(); j++) {
				JSONObject row2 = rs2.getJSONObject(j);
				itemToolbar = new JSONObject();
				itemToolbar.put("code", row2.getString("code"));
				itemToolbar.put("name", row2.getString("name"));

				arrToolbar.put(itemToolbar);
			}
			item.put("toolbar", arrToolbar);

			records.put(item);
		}
		r.put("records", records);
	}

	// ***************************************************************************************
	/** 加载select数据源 **/
	private void loadSelectOption(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("options"))
			throw new Exception("缺少参数");
		String params = BasicFunction.replaceSQL(vo.get("options"));

		JSONArray arr = new JSONArray(params);
		JSONObject item = null;
		JSONArray records = new JSONArray();

		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);

			item = new JSONObject();
			item.put("dataSourceId", obj.getString("dataSourceId"));
			item.put("textColumn", obj.getString("textColumn"));
			item.put("valueColumn", obj.getString("valueColumn"));

			String sql = "SELECT * FROM t_sys_datasource WHERE id=" + obj.getString("dataSourceId");
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				sql = rs.getJSONObject(0).getString("sql");
				JSONArray rs2 = dao.fillRS(sql);
				JSONArray arrOption = null;
				JSONObject itemOption = null;
				arrOption = new JSONArray();
				for (int j = 0; j < rs2.length(); j++) {
					itemOption = new JSONObject();
					itemOption.put("label", rs2.getJSONObject(j).getString(obj.getString("textColumn")));
					itemOption.put("value", rs2.getJSONObject(j).getString(obj.getString("valueColumn")));
					arrOption.put(itemOption);
				}
				item.put("option", arrOption);
			}
			records.put(item);
		}
		r.put("records", records);
	}

	// ***************************************************************************************
	/** 加载Cascader 级联选择器数据源 **/
	private void loadCascaderOption(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("options"))
			throw new Exception("缺少参数");
		String params = BasicFunction.replaceSQL(vo.get("options"));

		JSONArray arr = new JSONArray(params);
		JSONObject item = null;
		JSONArray records = new JSONArray();

		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			item = new JSONObject();
			item.put("dataSourceId", obj.getString("dataSourceId"));
			item.put("showAllLevels", obj.getString("showAllLevels"));
			JSONArray arrStepTemp = new JSONArray(obj.getString("options"));
			JSONArray arrStep = new JSONArray();
			for (int j = 0; j < arrStepTemp.length(); j++) {
				for (int k = 0; k < arrStepTemp.length(); k++) {
					if (arrStepTemp.getJSONObject(k).getInt("index") == j) {
						arrStep.put(arrStepTemp.getJSONObject(k));
					}
				}
			}

			String sql = "SELECT * FROM t_sys_datasource WHERE id=" + obj.getString("dataSourceId");
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				sql = rs.getJSONObject(0).getString("sql");
				JSONArray rs2 = dao.fillRS(sql);
				JSONArray arrOption = new JSONArray();
				arrOption = getChildrenCascader(rs2, arrStep, 0);
				item.put("option", arrOption);
			}
			records.put(item);
		}
		r.put("records", records);
	}

	private JSONArray getChildrenCascader(JSONArray c_arr, JSONArray c_arr_step, Integer c_step) throws Exception {
		JSONArray arr = new JSONArray();
		JSONObject item = null;
		String textColumn = c_arr_step.getJSONObject(c_step).getString("textColumn");
		String valueColumn = c_arr_step.getJSONObject(c_step).getString("valueColumn");

		for (int i = 0; i < c_arr.length(); i++) {
			boolean b = true;
			for (int j = 0; j < arr.length(); j++) {
				if (arr.getJSONObject(j).getString("value").equals(c_arr.getJSONObject(i).getString(valueColumn))) {
					b = false;
				}
			}
			if (b) {
				item = new JSONObject();
				item.put("label", c_arr.getJSONObject(i).getString(textColumn));
				item.put("value", c_arr.getJSONObject(i).getString(valueColumn));
				if (c_step + 1 < c_arr_step.length()) {
					JSONArray arrChildren = new JSONArray();
					for (int k = 0; k < c_arr.length(); k++) {
						if (c_arr.getJSONObject(k).getString(valueColumn)
								.equals(c_arr.getJSONObject(i).getString(valueColumn))) {
							arrChildren.put(c_arr.getJSONObject(k));
						}
					}
					item.put("children", getChildrenCascader(arrChildren, c_arr_step, c_step + 1));
				}
				arr.put(item);
			}
		}
		return arr;
	}

	// ***************************************************************************************
	/** 加载平台菜单 **/
	private void loadSysMenu(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		boolean b = false;
		if (vo.containsKey("isShowRight"))
			b = vo.containsKey("isShowRight");

		String sql = "SELECT * FROM (SELECT *,"
				+ " (select if(SUM(isBrowse)>0,1,0) AS browseRight from t_sys_role_right WHERE menuId=t_sys_menu.id AND roleId IN (select roleId from t_sys_user_role WHERE isDel=0 AND userId="
				+ s.userid + ")) AS browseRight" + " FROM t_sys_menu WHERE isStop=0 AND parentId=0" + " UNION ALL"
				+ " SELECT t_sys_menu.*,"
				+ " (select if(SUM(isBrowse)>0,1,0) AS browseRight from t_sys_role_right WHERE menuId=t_sys_menu.id AND roleId IN (select roleId from t_sys_user_role WHERE isDel=0 AND userId="
				+ s.userid + ")) AS browseRight"
				+ " FROM t_sys_menu,t_sys_menu t WHERE t_sys_menu.isStop=0 AND t_sys_menu.parentId=t.id AND t_sys_menu.parentId IN (SELECT id FROM t_sys_menu WHERE parentId=0)) t";

		if (b) {// 显示有权限的菜单
			sql = sql + " WHERE t.browseRight>0";
		}

		sql = sql + " ORDER BY parentId,listNo,id ASC";
		JSONArray rs = dao.fillRS(sql);

		JSONArray records = new JSONArray();
		JSONObject menu1 = null;
		JSONObject menu2 = null;
		for (int i = 0; i < rs.length(); i++) {
			JSONObject row = rs.getJSONObject(i);
			if (row.getInt("parentId") == 0) {
				menu1 = new JSONObject();
				menu1.put("id", row.getInt("id"));
				menu1.put("name", row.getString("name"));

				if (row.getString("icon").equals("")) {
					menu1.put("icon", "icon-all");
				} else {
					menu1.put("icon", row.getString("icon"));
				}

				menu1.put("url", "");
				records.put(menu1);
			}
		}
		for (int i = 0; i < rs.length(); i++) {
			JSONObject row = rs.getJSONObject(i);
			if (row.getInt("parentId") > 0) {
				menu2 = new JSONObject();
				menu2.put("id", row.getInt("id"));
				menu2.put("name", row.getString("name"));

				String strUrl = "";
				String strTable = "";
				switch (row.getInt("menuType")) {
				case 1:
					sql = "SELECT * FROM t_sys_module WHERE id=" + row.getString("params");
					JSONArray rs2 = dao.fillRS(sql);
					if (rs2.length() > 0) {
						strTable = rs2.getJSONObject(0).getString("tableName");
						strTable = AES128.Encrypt(strTable, MConfig.AES128_KEY_QUERY);

						switch (rs2.getJSONObject(0).getInt("moduleType")) {
						case 1:
							strUrl = "/StandardA";
							break;
						case 2:
							strUrl = "/StandardB";
							break;
						case 3:
							strUrl = "/StandardC";
							break;
						}
					}
					menu2.put("table", strTable);
					break;
				case 2:
					strUrl = "/Order";
					String strOrderId = row.getString("params");
					strOrderId = AES128.Encrypt(strOrderId, MConfig.AES128_KEY_QUERY);
					menu2.put("orderId", strOrderId);
					break;
				case 3:
					strUrl = "/Report";
					String strReportId = row.getString("params");
					strReportId = AES128.Encrypt(strReportId, MConfig.AES128_KEY_QUERY);
					menu2.put("orderId", strReportId);
					break;
				case 4:
					String strLink = row.getString("params");
					strUrl = "/" + strLink;
				}
				menu2.put("url", strUrl);

				menu1 = null;
				for (int j = 0; j < records.length(); j++) {
					if (records.getJSONObject(j).getInt("id") == row.getInt("parentId")) {
						menu1 = records.getJSONObject(j);
					}
				}
				if (menu1 != null) {
					JSONArray submenu = null;
					if (!menu1.has("submenu")) {
						submenu = new JSONArray();
						menu1.put("submenu", submenu);
					}
					submenu = menu1.getJSONArray("submenu");
					submenu.put(menu2);
				}
			}
		}

		r.put("records", records);
	}

	// ***************************************************************************************
	/** 修改密码 **/
	private void saveSysPwd(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("pwdOld"))
			throw new Exception("请输入旧密码");
		if (!vo.containsKey("pwdNew"))
			throw new Exception("请输入新密码");

		String pwdOld = DigestUtils
				.md5Hex(BasicFunction.getContentBytes(BasicFunction.replaceSQL(vo.get("pwdOld")), "utf-8"));
		String pwdNew = DigestUtils
				.md5Hex(BasicFunction.getContentBytes(BasicFunction.replaceSQL(vo.get("pwdNew")), "utf-8"));

		String sql = "SELECT * FROM t_sys_user WHERE username='" + s.username + "' AND pwd='" + pwdOld + "'";
		JSONArray rs = dao.fillRS(sql);

		if (rs.length() > 0) {
			sql = "UPDATE t_sys_user SET pwd='" + pwdNew + "' WHERE username='" + s.username + "'";
			dao.execute(sql);
		} else {
			throw new CommonException("原密码错误");
		}
	}

	// ***************************************************************************************
	/** 加载角色列表 **/
	private void loadSysRole(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("constr")) {
			constr = BasicFunction.replaceSQL(vo.get("constr"));
		}

		String sql = "SELECT * FROM t_sys_role WHERE isDel=0 " + constr + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存角色 **/
	private void saveSysRole(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("name"))
			throw new Exception("缺少参数");
		int id = Integer.parseInt(vo.get("id"));
		String name = BasicFunction.replaceSQL(vo.get("name"));

		VO vo1 = new VO();
		vo1.setProperty("name", name);
		if (vo.containsKey("code"))
			vo1.setProperty("code", BasicFunction.replaceSQL(vo.get("code")));
		if (vo.containsKey("remark"))
			vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));

		String sql = "";
		vo1.TableName = "t_sys_role";
		if (id > 0) {
			sql = "SELECT * FROM t_sys_role WHERE name='" + name + "' AND id<>" + id;
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				throw new Exception("名称已存在");
			} else {
				vo1.id = id;
				dao.update(vo1);
			}
		} else {
			sql = "SELECT * FROM t_sys_role WHERE name='" + name + "'";
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				throw new Exception("名称已存在");
			} else {
				dao.add(vo1);
			}
		}
	}

	// ***************************************************************************************
	/** 删除角色 **/
	private void deleteSysRole(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_sys_role SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);

		// 删除权限表
		sql = "DELETE FROM t_sys_role_right WHERE roleId IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 加载操作员角色权限列表 **/
	private void loadSysRoleRight(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("roleId"))
			throw new Exception("缺少参数");
		int c_id = Integer.parseInt(vo.get("roleId"));

		String sql = "SELECT * FROM t_sys_role_right WHERE roleId=" + c_id + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存操作员角色权限 **/
	private void saveSysRoleRight(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("roleId"))
			throw new Exception("缺少参数");
		int roleId = Integer.parseInt(vo.get("roleId"));

		JSONArray rights = new JSONArray(vo.get("rights"));

		String sql = "";
		sql = "DELETE FROM t_sys_role_right WHERE roleId=" + roleId;
		dao.execute(sql);

		for (int i = 0; i < rights.length(); i++) {
			JSONObject row = rights.getJSONObject(i);
			VO vo1 = new VO();
			vo1.TableName = "t_sys_role_right";
			vo1.setProperty("roleId", roleId + "");
			vo1.setProperty("menuId", row.getString("menuId"));
			if (row.getString("isBrowse").equals("true")) {
				vo1.setProperty("isBrowse", "1");
			} else {
				vo1.setProperty("isBrowse", "0");
			}
			if (row.getString("isAdd").equals("true")) {
				vo1.setProperty("isAdd", "1");
			} else {
				vo1.setProperty("isAdd", "0");
			}
			if (row.getString("isEdit").equals("true")) {
				vo1.setProperty("isEdit", "1");
			} else {
				vo1.setProperty("isEdit", "0");
			}
			if (row.getString("isDelete").equals("true")) {
				vo1.setProperty("isDelete", "1");
			} else {
				vo1.setProperty("isDelete", "0");
			}
			if (row.getString("isStop").equals("true")) {
				vo1.setProperty("isStop", "1");
			} else {
				vo1.setProperty("isStop", "0");
			}
			if (row.getString("isRun").equals("true")) {
				vo1.setProperty("isRun", "1");
			} else {
				vo1.setProperty("isRun", "0");
			}
			if (row.getString("isSetting").equals("true")) {
				vo1.setProperty("isSetting", "1");
			} else {
				vo1.setProperty("isSetting", "0");
			}
			if (row.getString("isPrint").equals("true")) {
				vo1.setProperty("isPrint", "1");
			} else {
				vo1.setProperty("isPrint", "0");
			}
			if (row.getString("isExcel").equals("true")) {
				vo1.setProperty("isExcel", "1");
			} else {
				vo1.setProperty("isExcel", "0");
			}
			dao.add(vo1);
		}
	}

	// ***************************************************************************************
	/** 加载通用列表 **/
	private void loadList(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("table"))
			throw new Exception("缺少参数");

		String constr = "";
		if (vo.containsKey("constr")) {
			constr = BasicFunction.replaceSQL(vo.get("constr"));
		}

		String orderstr = "ORDER BY id DESC";
		if (vo.containsKey("orderstr")) {
			orderstr = BasicFunction.replaceSQL(vo.get("orderstr"));
		}

		String table = BasicFunction.replaceSQL(vo.get("table"));
		table = AES128.Decrypt(table, MConfig.AES128_KEY_QUERY);

		String sql = "SELECT * FROM " + table + " WHERE isDel=0 " + constr + " " + orderstr;
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 删除数据 **/
	private void deleteData(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("table"))
			throw new Exception("缺少参数");

		String table = BasicFunction.replaceSQL(vo.get("table"));
		table = AES128.Decrypt(table, MConfig.AES128_KEY_QUERY);
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String isDel = "1";
		if (vo.containsKey("isDel"))
			isDel = BasicFunction.replaceSQL(vo.get("isDel"));

		String sql = "UPDATE " + table + " SET isDel=" + isDel + " WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 停用、启用数据 **/
	private void stopData(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("table"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("isStop"))
			throw new Exception("缺少参数");

		String table = BasicFunction.replaceSQL(vo.get("table"));
		table = AES128.Decrypt(table, MConfig.AES128_KEY_QUERY);
		String id = BasicFunction.replaceSQL(vo.get("id"));
		String isStop = BasicFunction.replaceSQL(vo.get("isStop"));

		String sql = "UPDATE " + table + " SET isStop=" + isStop + " WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 审核、反审数据 **/
	private void checkData(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("table"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("isCheck"))
			throw new Exception("缺少参数");

		String table = BasicFunction.replaceSQL(vo.get("table"));
		table = AES128.Decrypt(table, MConfig.AES128_KEY_QUERY);
		String id = BasicFunction.replaceSQL(vo.get("id"));
		String isCheck = BasicFunction.replaceSQL(vo.get("isCheck"));

		String sql = "UPDATE " + table + " SET isChecked=" + isCheck + " WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 保存FormB数据 **/
	private void saveStandardB(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("code"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("table"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int id = Integer.parseInt(vo.get("id"));
		String code = BasicFunction.replaceSQL(vo.get("code"));
		String name = "";
		if (vo.containsKey("name"))
			name = BasicFunction.replaceSQL(vo.get("name"));
		String table = BasicFunction.replaceSQL(vo.get("table"));
		table = AES128.Decrypt(table, MConfig.AES128_KEY_QUERY);

		vo1.setProperty("code", code);
		vo1.setProperty("name", name);
		vo1.TableName = table;

		if (id > 0) {
			vo1.id = id;
			dao.update(vo1);
		} else {
			vo1.TableName = table;
			int c_id = dao.add(vo1);
			r.put("id", c_id);
		}
	}

	// ***************************************************************************************
	/** 加载动态表单列表 **/
	private void loadOrder(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("orderId"))
			throw new Exception("缺少参数");
		String orderId = BasicFunction.replaceSQL(vo.get("orderId"));
		orderId = AES128.Decrypt(orderId, MConfig.AES128_KEY_QUERY);

		int datagridId = 0;
		String sql = "SELECT dataGridId FROM t_sys_order WHERE id=" + orderId;
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			datagridId = rs.getJSONObject(0).getInt("dataGridId");
		}

		sql = "SELECT * FROM t_sys_datagrid WHERE id=" + datagridId;
		rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			JSONObject row = rs.getJSONObject(0);
			String sqlstr = row.getString("sql");

			String constr = "";
			JSONArray arrConstr2 = new JSONArray();
			constr = BasicFunction.replaceSQL(vo.get("constr"));
			if (constr.equals("[]")) {
				constr = "";
			} else {
				JSONArray arrConstr = new JSONArray(constr);
				constr = "";
				int j = 0;
				for (int i = 0; i < arrConstr.length(); i++) {
					JSONObject itemConstr = arrConstr.getJSONObject(i);

					if (itemConstr.getInt("conditionType") == 1) {
						if (j == 0) {
							constr = " WHERE ";
						} else {
							constr = constr + " AND ";
						}

						if (itemConstr.getInt("controlType") == 2) {
							if (BasicFunction.isInteger(itemConstr.getString("value"))) { // 整数类型
								constr = constr + itemConstr.getString("columnName") + "="
										+ itemConstr.getString("value");
							} else {
								constr = constr + itemConstr.getString("columnName") + "='"
										+ itemConstr.getString("value") + "'";
							}

						} else {
							constr = constr + itemConstr.getString("columnName") + " LIKE '%"
									+ itemConstr.getString("value") + "%'";
						}

						j = j + 1;
					} else {
						arrConstr2.put(itemConstr);
					}
				}
			}

			if (!row.getString("params").equals("")) {
				JSONArray arrParams = new JSONArray(row.getString("params"));
				for (int i = 0; i < arrParams.length(); i++) {
					JSONObject itemParams = arrParams.getJSONObject(i);
					boolean hasParamValue = false;

					for (int j = 0; j < arrConstr2.length(); j++) {
						if (itemParams.getString("name").equals(arrConstr2.getJSONObject(j).getString("columnName"))) {
							sqlstr = sqlstr.replace("@" + itemParams.getString("name"),
									arrConstr2.getJSONObject(j).getString("value"));
							hasParamValue = true;
						}
					}
					if (!hasParamValue)
						sqlstr = sqlstr.replace("@" + itemParams.getString("name"),
								itemParams.getString("defaultValue"));
				}

			}

			sqlstr = "SELECT * FROM (" + sqlstr + ")t" + constr;

			JSONArray rs2 = dao.fillRS(sqlstr);
			r.put("records", rs2);
		} else {
			throw new Exception("找不到数据");
		}
	}

	// ***************************************************************************************
	/** 保存动态表单数据 **/
	private void saveOrder(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("table"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int id = Integer.parseInt(vo.get("id"));
		String table = BasicFunction.replaceSQL(vo.get("table"));
		table = AES128.Decrypt(table, MConfig.AES128_KEY_QUERY);

		String formstr = "";
		if (vo.containsKey("formstr")) {
			formstr = BasicFunction.replaceSQL(vo.get("formstr"));
		}
		if (formstr.length() > 0) {
			JSONObject obj = new JSONObject(formstr);
			Iterator it = obj.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = obj.getString(key);
				vo1.setProperty(key, value);
			}
		}

		vo1.TableName = table;
		if (id > 0) {
			vo1.id = id;
			dao.update(vo1);
		} else {
			vo1.TableName = table;
			id = dao.add(vo1);
			r.put("addedId", id);
		}
	}

	// ***************************************************************************************
	/** 另存为动态表单数据 **/
	private void saveasOrder(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id1"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("id2"))
			throw new Exception("缺少参数");
		JSONArray tabPages = new JSONArray(BasicFunction.replaceSQL(vo.get("tabPages")));

		int id1 = Integer.parseInt(vo.get("id1"));
		int id2 = Integer.parseInt(vo.get("id2"));

		for (int i = 0; i < tabPages.length(); i++) {
			if (tabPages.getJSONObject(i).has("childrenOrderId")) {
				String childrenOrderId = AES128.Decrypt(tabPages.getJSONObject(i).getString("childrenOrderId"),
						MConfig.AES128_KEY_QUERY);

				String sql = "SELECT t_sys_module.tableName, t_sys_order.params FROM t_sys_order ";
				sql = sql + " LEFT OUTER JOIN t_sys_module ON t_sys_order.moduleId=t_sys_module.id";
				sql = sql + " WHERE t_sys_order.id=" + childrenOrderId;
				JSONArray rs = dao.fillRS(sql);
				if (rs.length() > 0) {
					String table = rs.getJSONObject(0).getString("tableName");
					JSONObject params = new JSONObject(rs.getJSONObject(0).getString("params"));
					String key = params.getString("key");
					sql = "SELECT * from " + table + " WHERE " + key + "=" + id1 + " AND isDel=0 ORDER BY id ASC";
					JSONArray rs2 = dao.fillRS(sql);
					for (int j = 0; j < rs2.length(); j++) {
						JSONObject row = rs2.getJSONObject(j);

						VO vo1 = new VO();
						vo1.TableName = table;

						Iterator it = row.keys();
						while (it.hasNext()) {
							String itKey = (String) it.next();
							if (!itKey.equals("id") && !itKey.equals(key)) {
								String itValue = row.getString(itKey);
								vo1.setProperty(itKey, itValue);
							}
						}
						vo1.setProperty(key, id2 + "");
						dao.add(vo1);
					}
				}
			}
		}
	}

	// ***************************************************************************************
	/** 保存列表配置v20 **/
	private void saveDatagridDetail2(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		JSONArray arr = new JSONArray(vo.get("arrItem"));
		for (int i = 0; i < arr.length(); i++) {
			JSONObject row = arr.getJSONObject(i);

			String width = "0";
			if (!row.getString("width").equals(""))
				width = row.getString("width");
			String sql = "UPDATE t_sys_datagrid_detail SET itemName='" + row.getString("itemName") + "',width=" + width
					+ ",isVisible=" + row.getString("isVisible") + " WHERE id=" + row.getString("id");
			dao.execute(sql);
		}
	}

	// ***************************************************************************************
	/** 加载报修单列表 **/
	private void loadRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals("")) && (!vo.get("dateOrder").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("dateBegin") && (!vo.get("dateBegin").equals("")) && (!vo.get("dateBegin").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateBegin"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateBegin BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("dateEnd") && (!vo.get("dateEnd").equals("")) && (!vo.get("dateEnd").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateEnd"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateEnd BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("orderCode") && (!vo.get("orderCode").equals(""))) {
			constr = constr + " AND orderCode LIKE '%" + BasicFunction.replaceSQL(vo.get("orderCode")) + "%'";
		}
		if (vo.containsKey("group") && (!vo.get("group").equals(""))) {
			constr = constr + " AND `group` LIKE '%" + BasicFunction.replaceSQL(vo.get("group")) + "%'";
		}
		if (vo.containsKey("employee") && (!vo.get("employee").equals(""))) {
			constr = constr + " AND employee LIKE '%" + BasicFunction.replaceSQL(vo.get("employee")) + "%'";
		}
		if (vo.containsKey("repairTypeId") && (!vo.get("repairTypeId").equals(""))) {
			constr = constr + " AND repairTypeId IN (" + BasicFunction.replaceSQL(vo.get("repairTypeId")) + ")";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr = constr + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr = constr + " AND carType IN ('" + BasicFunction.replaceSQL(vo.get("carType")).replaceAll(",", "','") + "')";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr = constr + " AND department LIKE '%" + BasicFunction.replaceSQL(vo.get("department")) + "%'";
		}
		if (vo.containsKey("customerId") && (!vo.get("customerId").equals(""))) {
			constr = constr + " AND customerId=" + BasicFunction.replaceSQL(vo.get("customerId"));
		}
		if (vo.containsKey("dump") && (!vo.get("dump").equals(""))) {
			constr = constr + " AND dump LIKE '%" + BasicFunction.replaceSQL(vo.get("dump")) + "%'";
		}
		if (vo.containsKey("isChecked") && (!vo.get("isChecked").equals(""))) {
			if (vo.get("isChecked").equals("true")) {
				constr = constr + " AND isChecked=1";
			} else {
				constr = constr + " AND isChecked=0";
			}
		}
		if (vo.containsKey("project") && (!vo.get("project").equals(""))) {
			constr = constr + " AND id in(SELECT orderId FROM t_repair_detail WHERE project LIKE '%"
					+ BasicFunction.replaceSQL(vo.get("project")) + "%')";
		}
		if (vo.containsKey("itemCode") && (!vo.get("itemCode").equals(""))) {
			constr = constr
					+ " AND (orderCode in(SELECT orderCodeRepair FROM v_storage_out_detail WHERE itemCode LIKE '%"
					+ BasicFunction.replaceSQL(vo.get("itemCode")) + "%') AND repairTypeId!=3)";
		}
		if (vo.containsKey("itemName") && (!vo.get("itemName").equals(""))) {
			constr = constr
					+ " AND (orderCode in(SELECT orderCodeRepair FROM v_storage_out_detail WHERE itemName LIKE '%"
					+ BasicFunction.replaceSQL(vo.get("itemName")) + "%') AND repairTypeId!=3)";
		}

		String sql = "SELECT * FROM v_repair WHERE isDel=0 " + constr + " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载报修类别列表 **/
	private void loadRepairType(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("isStop") && (!vo.get("isStop").equals(""))) {
			constr = constr + " AND isStop=0";
		}

		String sql = "SELECT id,name,isStop FROM t_repair_type WHERE isDel=0 " + constr + " ORDER BY code,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载报修详情列表 **/
	private void loadRepairDetail(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("editId"))
			throw new Exception("缺少参数");

		String constr = "";
		constr = constr + " AND orderId=" + BasicFunction.replaceSQL(vo.get("editId"));

		String sql = "SELECT * FROM t_repair_detail WHERE isDel=0 AND isStop=0 " + constr + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载报修详情列表 **/
	private void loadRepairDetailByOrdercode(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("orderCode")) throw new Exception("缺少参数");

		String constr = "";
		constr = constr + " AND orderCode='" + BasicFunction.replaceSQL(vo.get("orderCode")) +"'";

		String sql = "SELECT * FROM v_repair_detail WHERE isDel=0 AND isStop=0 " + constr + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存报修单 **/
	private void saveRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		int repairTypeId = 0;
		String orderCode = "";
		String dateOrder = "";
		
		if (vo.containsKey("repairTypeId")) {
			repairTypeId=Integer.parseInt(vo.get("repairTypeId"));
			vo1.setProperty("repairTypeId", BasicFunction.replaceSQL(vo.get("repairTypeId")));
		}
		if (vo.containsKey("group"))vo1.setProperty("group", BasicFunction.replaceSQL(vo.get("group")));
		if (vo.containsKey("employee"))vo1.setProperty("employee", BasicFunction.replaceSQL(vo.get("employee")));
		if (vo.containsKey("customerId"))vo1.setProperty("customerId", BasicFunction.replaceSQL(vo.get("customerId")));
		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("dumpId"))vo1.setProperty("dumpId", BasicFunction.replaceSQL(vo.get("dumpId")));
		if (vo.containsKey("dateBegin"))vo1.setProperty("dateBegin", BasicFunction.replaceSQL(vo.get("dateBegin")));
		if (vo.containsKey("dateEnd") && (!vo.get("dateEnd").equals("")) && (!vo.get("dateEnd").equals("null"))) {
			vo1.setProperty("dateEnd", BasicFunction.replaceSQL(vo.get("dateEnd")));
		}
		if (vo.containsKey("countDistance1"))vo1.setProperty("countDistance1", BasicFunction.replaceSQL(vo.get("countDistance1")));
		if (vo.containsKey("countDistance2"))vo1.setProperty("countDistance2", BasicFunction.replaceSQL(vo.get("countDistance2")));
		if (vo.containsKey("remark"))vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));
		vo1.setProperty("userId", s.userid+"");

		vo1.TableName = "t_repair";
		if (c_id > 0) {
			if (vo.containsKey("orderCode"))orderCode=BasicFunction.replaceSQL(vo.get("orderCode"));
			if (vo.containsKey("dateOrder"))dateOrder=BasicFunction.replaceSQL(vo.get("dateOrder"));
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			// 获取当时时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
			Date now = new Date(System.currentTimeMillis());
			dateOrder = dateFormat.format(now);
			String date2 = dateFormat2.format(now);
			vo1.setProperty("dateOrder", dateOrder);

			// 获取自动生成单号
			String sql = "SELECT CONCAT(" + date2 + ", LPAD(FLOOR(RAND() * 1000),3,0)) AS name";
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				orderCode = rs.getJSONObject(0).getString("name");
			}
			vo1.setProperty("orderCode", orderCode);

			c_id = dao.add(vo1);
			r.put("addedId", c_id);
			r.put("orderCode", orderCode);
			r.put("dateOrder", dateOrder);
		}

		// 删除详细表
		String sql = "DELETE FROM t_repair_detail WHERE orderId=" + c_id;
		dao.execute(sql);

		// 保存详细表
		JSONArray detail = new JSONArray(BasicFunction.replaceSQL(vo.get("detail")));
		for (int i = 0; i < detail.length(); i++) {
			JSONObject itemDetail = detail.getJSONObject(i);

			VO vo2 = new VO();
			vo2.TableName = "t_repair_detail";
			vo2.setProperty("orderId", c_id + "");
			vo2.setProperty("project", itemDetail.getString("project"));
			vo2.setProperty("hours", itemDetail.getString("hours"));
			vo2.setProperty("price", itemDetail.getString("price"));
			vo2.setProperty("employee", itemDetail.getString("employee"));
			vo2.setProperty("remark", itemDetail.getString("remark"));
			dao.add(vo2);
		}
		
		if(repairTypeId==3 && (!orderCode.equals(""))) {	//保存外修单领料
			int storageOutId =  0;
			
			sql = "SELECT id FROM t_storage_out WHERE isDel=0 AND orderCodeRepair='" + orderCode + "'";
			JSONArray rs = dao.fillRS(sql);
			if (rs.length() > 0) {
				storageOutId =  rs.getJSONObject(0).getInt("id");
			}else {
				// 新建领料基础表
				VO vo3 = new VO();
				vo3.setProperty("type", "3");
				vo3.setProperty("dateOrder", dateOrder);
				vo3.setProperty("orderCodeRepair", orderCode);
				vo3.setProperty("userId", s.userid+"");
				vo3.TableName = "t_storage_out";
				storageOutId = dao.add(vo3);
			}
			
			// 删除领料详细表
			sql = "DELETE FROM t_storage_out_detail WHERE orderId=" + storageOutId;
			dao.execute(sql);
			
			// 保存领料详细表
			JSONArray storageOutDetail = new JSONArray(BasicFunction.replaceSQL(vo.get("storageOutDetail")));
			for (int i = 0; i < storageOutDetail.length(); i++) {
				JSONObject itemStorageOutDetail = storageOutDetail.getJSONObject(i);

				VO vo4 = new VO();
				vo4.TableName = "t_storage_out_detail";
				vo4.setProperty("orderId", storageOutId + "");
				vo4.setProperty("itemName", itemStorageOutDetail.getString("itemName"));
				vo4.setProperty("count", itemStorageOutDetail.getString("count"));
				vo4.setProperty("price", itemStorageOutDetail.getString("price"));
				dao.add(vo4);
			}
		}
	}

	// ***************************************************************************************
	/** 删除报修单 **/
	private void deleteRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));
		boolean b=true;
		
		String sql = "SELECT * FROM t_repair WHERE id IN (" + id + ")";
		JSONArray rs = dao.fillRS(sql);
		for (int i = 0; i < rs.length(); i++) {
			String ordercode = rs.getJSONObject(i).getString("orderCode");

			sql = "SELECT * FROM v_storage_out_detail WHERE isDel=0 AND orderCodeRepair='" + ordercode + "'";
			JSONArray rs2 = dao.fillRS(sql);
			if (rs2.length() > 0) {
				b=false;
				throw new Exception("此报修单已领料，不可删除");
			}
		}
		
		if(b) {
			sql = "UPDATE t_repair SET isDel=1 WHERE id IN (" + id + ")";
			dao.execute(sql);

			sql = "UPDATE t_repair_detail SET isDel=1 WHERE orderId IN (" + id + ")";
			dao.execute(sql);
		}
	}

	// ***************************************************************************************
	/** 审核报修单 **/
	private void checkRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		// 获取当时时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date(System.currentTimeMillis());
		String dateEnd = dateFormat.format(now);

		String sql = "SELECT dateEnd FROM t_repair WHERE id=" + id;
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			if(!rs.getJSONObject(0).getString("dateEnd").equals("")) {
				dateEnd =  rs.getJSONObject(0).getString("dateEnd");
			}
		}
		
		VO vo1 = new VO();
		vo1.setProperty("isChecked", "1");
		vo1.setProperty("dateEnd", dateEnd);
		vo1.TableName = "t_repair";
		vo1.id = Integer.parseInt(id);
		dao.update(vo1);

		r.put("dateEnd", dateEnd);
	}

	// ***************************************************************************************
	/** 反审报修单 **/
	private void uncheckRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_repair SET isChecked=0 WHERE id=" + id;
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 加载报修今天在修、本月完成维修、昨天完成修理 **/
	private void loadRepairCount(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		
		int countToday=0; 
		int countMonth=0; 
		int countYesterday=0; 
		
		String sql = "select count(id) as countToday from t_repair where isChecked=0 and isDel=0";
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			countToday=rs.getJSONObject(0).getInt("countToday");
		}
		
		sql = "select count(id) as countYesterday from t_repair where isChecked=1 and isDel=0 and to_days(now())-to_days(dateEnd) =1";
		rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			countYesterday=rs.getJSONObject(0).getInt("countYesterday");
		}
		
		sql = "select count(id) as countMonth from t_repair where isChecked=1 and isDel=0 and date_format(curdate(),'%y%m') = date_format(dateEnd,'%y%m')";
		rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			countMonth=rs.getJSONObject(0).getInt("countMonth");
		}

		r.put("countToday", countToday);
		r.put("countMonth", countMonth);
		r.put("countYesterday", countYesterday);
	}

	// ***************************************************************************************
	/** 加载部门列表 **/
	private void loadDepartment(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT * FROM t_department WHERE isDel=0 ORDER BY code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存部门 **/
	private void saveDepartment(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id")) throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("code"))vo1.setProperty("code", BasicFunction.replaceSQL(vo.get("code")));
		if (vo.containsKey("name"))vo1.setProperty("name", BasicFunction.replaceSQL(vo.get("name")));
		if (vo.containsKey("master"))vo1.setProperty("master", BasicFunction.replaceSQL(vo.get("master")));
		if (vo.containsKey("departmentId"))vo1.setProperty("departmentId", BasicFunction.replaceSQL(vo.get("departmentId")));
		if (vo.containsKey("department"))vo1.setProperty("department", BasicFunction.replaceSQL(vo.get("department")));

		vo1.TableName = "t_department";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}
	}

	// ***************************************************************************************
	/** 删除部门 **/
	private void deleteDepartment(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_department SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 加载车辆权属列表 **/
	private void loadCompany(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT * FROM t_company WHERE isDel=0 ORDER BY code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载维修班组列表 **/
	private void loadGroup(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT name FROM t_group WHERE isDel=0 AND isStop=0 ORDER BY code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载供应商列表 **/
	private void loadCustomer(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT id,code,name FROM t_customer WHERE isDel=0 AND isStop=0 ORDER BY code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载设施类别列表 **/
	private void loadDumpType(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT id,code,name FROM t_dump_type WHERE isDel=0 AND isStop=0 ORDER BY code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载供应商列表 **/
	private void loadDump(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT id,code,name,dumpType FROM v_dump WHERE isDel=0 AND isStop=0 ORDER BY dumpType,code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载供应商列表 **/
	private void loadEmployee(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT id,code,name FROM t_employee WHERE isDel=0 AND isStop=0 ORDER BY code,name,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载车牌号列表 **/
	private void loadCar(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("code") && (!vo.get("code").equals(""))) {
			constr = constr + " AND code LIKE '%" + BasicFunction.replaceSQL(vo.get("code")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr = constr + " AND carType IN ('" + BasicFunction.replaceSQL(vo.get("carType")).replaceAll(",", "','") + "')";
		}
		if (vo.containsKey("VIN") && (!vo.get("VIN").equals(""))) {
			constr = constr + " AND VIN LIKE '%" + BasicFunction.replaceSQL(vo.get("VIN")) + "%'";
		}
		if (vo.containsKey("VIN2") && (!vo.get("VIN2").equals(""))) {
			constr = constr + " AND VIN2 LIKE '%" + BasicFunction.replaceSQL(vo.get("VIN2")) + "%'";
		}
		if (vo.containsKey("engineNo") && (!vo.get("engineNo").equals(""))) {
			constr = constr + " AND engineNo LIKE '%" + BasicFunction.replaceSQL(vo.get("engineNo")) + "%'";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr = constr + " AND department='" + BasicFunction.replaceSQL(vo.get("department")) + "'";
		}
		if (vo.containsKey("remark") && (!vo.get("remark").equals(""))) {
			constr = constr + " AND remark LIKE '%" + BasicFunction.replaceSQL(vo.get("remark")) + "%'";
		}
		if (vo.containsKey("isStop") && (!vo.get("isStop").equals(""))) {
			constr = constr + " AND isStop=0";
		}

		String order = "code,carNo,";
		if (vo.containsKey("order") && (!vo.get("order").equals("")) && (!vo.get("order").equals("carNo"))) {
			order = "carNo,code,";
		}

		String sql = "SELECT * FROM v_car WHERE isDel=0" + constr + " ORDER BY " + order + " id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载车辆类型列表 **/
	private void loadCarType(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT id,code,name FROM t_car_type WHERE isDel=0 AND isStop=0 ORDER BY code,name ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存年审 **/
	private void saveCar(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("code"))vo1.setProperty("code", BasicFunction.replaceSQL(vo.get("code")));
		if (vo.containsKey("carNo"))vo1.setProperty("carNo", BasicFunction.replaceSQL(vo.get("carNo")));
		if (vo.containsKey("carTypeId"))vo1.setProperty("carTypeId", BasicFunction.replaceSQL(vo.get("carTypeId")));
		if (vo.containsKey("VIN"))vo1.setProperty("VIN", BasicFunction.replaceSQL(vo.get("VIN")));
		if (vo.containsKey("VIN2"))vo1.setProperty("VIN2", BasicFunction.replaceSQL(vo.get("VIN2")));
		if (vo.containsKey("engineNo"))vo1.setProperty("engineNo", BasicFunction.replaceSQL(vo.get("engineNo")));
		if (vo.containsKey("departmentId"))vo1.setProperty("departmentId", BasicFunction.replaceSQL(vo.get("departmentId")));
		if (vo.containsKey("factory"))vo1.setProperty("factory", BasicFunction.replaceSQL(vo.get("factory")));
		if (vo.containsKey("cardOil"))vo1.setProperty("cardOil", BasicFunction.replaceSQL(vo.get("cardOil")));
		if (vo.containsKey("remark"))vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));
		if (vo.containsKey("price"))vo1.setProperty("price", BasicFunction.replaceSQL(vo.get("price")));
		if (vo.containsKey("company"))vo1.setProperty("company", BasicFunction.replaceSQL(vo.get("company")));
		if (vo.containsKey("dateBuy")) {
			if(vo.get("dateBuy").equals("")) {
				vo1.setProperty("dateBuy", null);
			}else {
				vo1.setProperty("dateBuy", BasicFunction.replaceSQL(vo.get("dateBuy")));
			}
		}
		if (vo.containsKey("dateLicence")) {
			if(vo.get("dateLicence").equals("")) {
				vo1.setProperty("dateLicence", null);
			}else {
				vo1.setProperty("dateLicence", BasicFunction.replaceSQL(vo.get("dateLicence")));
			}
		}
		if (vo.containsKey("employee"))vo1.setProperty("employee", BasicFunction.replaceSQL(vo.get("employee")));

		vo1.TableName = "t_car";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}
	}

	// ***************************************************************************************
	/** 删除报修单 **/
	private void deleteCar(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_car SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 加载安监列表 **/
	private void loadSafety(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("type") && (!vo.get("type").equals(""))) {
			constr = constr + " AND type=" + BasicFunction.replaceSQL(vo.get("type"));
		}
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals(""))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("code") && (!vo.get("code").equals(""))) {
			constr = constr + " AND code LIKE '%" + BasicFunction.replaceSQL(vo.get("code")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr = constr + " AND carType='" + BasicFunction.replaceSQL(vo.get("carType")) + "'";
		}
		if (vo.containsKey("VIN") && (!vo.get("VIN").equals(""))) {
			constr = constr + " AND VIN LIKE '%" + BasicFunction.replaceSQL(vo.get("VIN")) + "%'";
		}
		if (vo.containsKey("engineNo") && (!vo.get("engineNo").equals(""))) {
			constr = constr + " AND engineNo LIKE '%" + BasicFunction.replaceSQL(vo.get("engineNo")) + "%'";
		}
		if (vo.containsKey("company") && (!vo.get("company").equals(""))) {
			constr = constr + " AND company LIKE '%" + BasicFunction.replaceSQL(vo.get("company")) + "%'";
		}
		if (vo.containsKey("carId") && (!vo.get("carId").equals(""))) {
			constr = constr + " AND carId =" + BasicFunction.replaceSQL(vo.get("carId"));
		}

		String sql = "SELECT * FROM v_safety WHERE isDel=0 AND isStop=0 " + constr + " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存安监 **/
	private void saveSafety(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("type"))vo1.setProperty("type", BasicFunction.replaceSQL(vo.get("type")));
		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("dateOrder"))vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("company"))vo1.setProperty("company", BasicFunction.replaceSQL(vo.get("company")));
		if (vo.containsKey("price"))vo1.setProperty("price", BasicFunction.replaceSQL(vo.get("price")));
		if (vo.containsKey("orderCode"))vo1.setProperty("orderCode", BasicFunction.replaceSQL(vo.get("orderCode")));
		vo1.setProperty("userId", s.userid+"");

		vo1.TableName = "t_safety";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}
	}

	// ***************************************************************************************
	/** 删除安监 **/
	private void deleteSafety(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_safety SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 保存安监图片 **/
	private void saveSafetyImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));

		int imagesId = 0;
		imagesId = saveSysImages(vo, r);
		vo1.setProperty("imagesId", imagesId + "");

		if (vo.containsKey("type"))vo1.setProperty("type", BasicFunction.replaceSQL(vo.get("type")));
		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("dateOrder"))vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("company"))vo1.setProperty("company", BasicFunction.replaceSQL(vo.get("company")));
		if (vo.containsKey("price"))vo1.setProperty("price", BasicFunction.replaceSQL(vo.get("price")));
		if (vo.containsKey("orderCode"))vo1.setProperty("orderCode", BasicFunction.replaceSQL(vo.get("orderCode")));
		vo1.setProperty("userId", s.userid+"");

		vo1.TableName = "t_safety";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}
	}

	// ***************************************************************************************
	/** 导入安监数据 **/
	private void importSafety(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("srcL"))throw new Exception("缺少参数");
		if (!vo.containsKey("type"))throw new Exception("缺少参数");
		
		int countSuccess=0;
		int countError=0;
		String urlLog="";

		File file=new File(s.config.UPLOAD_PATH + "/temp/" +BasicFunction.replaceSQL(vo.get("srcL")));
		ArrayList<ArrayList<Object>> arr=ExcelUtil.readExcel(file);
		
		ArrayList<ArrayList<Object>> arrLog = new ArrayList<ArrayList<Object>>();
		arrLog.add(arr.get(0));
		
		for(int i=1;i<arr.size();i++) {
			ArrayList row=arr.get(i);
			if(!(row.get(1).equals("") && row.get(2).equals(""))) {	//车辆编码和车牌号不同时为空
				String sql="SELECT id FROM t_car WHERE code='" + row.get(1) + "' AND carNo ='" + row.get(2) + "'";
				JSONArray rs = dao.fillRS(sql);
				if(rs.length()>0) {
			        try {  
			        	int carId=rs.getJSONObject(0).getInt("id");
						
						VO vo1 = new VO();
						vo1.setProperty("type", BasicFunction.replaceSQL(vo.get("type")));
						vo1.setProperty("dateOrder", row.get(0)+"");
						vo1.setProperty("carId", carId+"");
						vo1.setProperty("price", row.get(3)+"");
						if(BasicFunction.replaceSQL(vo.get("type")).equals("3")) {
							vo1.setProperty("company", row.get(4)+"");
							vo1.setProperty("orderCode", row.get(5)+"");
						}
						vo1.TableName = "t_safety";
						dao.add(vo1);
						
						countSuccess=countSuccess+1;
			        } catch (Exception e) {  
						arrLog.add(row);
						countError=countError+1;
			        } 
				}else {
					arrLog.add(row);
					countError=countError+1;
				}
			}
		}

		if(arrLog.size()>1) {
			String savePath = s.config.UPLOAD_PATH + "/excel/upload/";  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
	        String ymd = sdf.format(new Date());  
	        savePath += ymd + "/";  
	        File dirFile = new File(savePath);  
	        if (!dirFile.exists()) {  
	            dirFile.mkdirs();  
	        }  
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + ".xls";  
            urlLog = ymd + "/" + newFileName;
	        
			ExcelUtil.writeExcel(arrLog, savePath + newFileName);
		}
		
		r.put("countSuccess", countSuccess);
		r.put("countError", countError);
		r.put("urlLog", urlLog);
	}
	
	// ***************************************************************************************
	/** 加载油耗列表 **/
	private void loadOil(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals(""))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("code") && (!vo.get("code").equals(""))) {
			constr = constr + " AND code LIKE '%" + BasicFunction.replaceSQL(vo.get("code")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr = constr + " AND carType='" + BasicFunction.replaceSQL(vo.get("carType")) + "'";
		}
		if (vo.containsKey("VIN") && (!vo.get("VIN").equals(""))) {
			constr = constr + " AND VIN LIKE '%" + BasicFunction.replaceSQL(vo.get("VIN")) + "%'";
		}
		if (vo.containsKey("engineNo") && (!vo.get("engineNo").equals(""))) {
			constr = constr + " AND engineNo LIKE '%" + BasicFunction.replaceSQL(vo.get("engineNo")) + "%'";
		}

		String sql = "SELECT t.*, IF((t.countOil0=0 || t.countDistance0=0 || t.countDistance<t.countDistance0),0,ROUND((t.countDistance-t.countDistance0)/t.countOil0,2)) AS rateOil FROM ";
		sql = sql
				+ " (SELECT *, IFNULL((SELECT MIN(t_oil3.countDistance) FROM t_oil AS t_oil3 WHERE t_oil3.isDel=0 AND t_oil3.carId=v_oil.carId AND DATE_FORMAT(t_oil3.dateOrder, '%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(t_oil2.dateOrder, '%Y-%m-%d')) FROM t_oil AS t_oil2 WHERE t_oil2.carId=v_oil.carId AND DATE_FORMAT(t_oil2.dateOrder, '%Y-%m-%d')<DATE_FORMAT(v_oil.dateOrder, '%Y-%m-%d'))),0) AS countDistance0,";
		sql = sql
				+ " IFNULL((SELECT SUM(t_oil3.countOil) FROM t_oil AS t_oil3 WHERE t_oil3.isDel=0 AND t_oil3.carId=v_oil.carId AND DATE_FORMAT(t_oil3.dateOrder, '%Y-%m-%d')=(SELECT MAX(DATE_FORMAT(t_oil2.dateOrder, '%Y-%m-%d')) FROM t_oil AS t_oil2 WHERE t_oil2.carId=v_oil.carId AND DATE_FORMAT(t_oil2.dateOrder, '%Y-%m-%d')<DATE_FORMAT(v_oil.dateOrder, '%Y-%m-%d'))),0) AS countOil0";
		sql = sql + " FROM v_oil WHERE isDel=0 " + constr + ")t";
		sql = sql + " ORDER BY t.dateOrder DESC,t.id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载油耗列表 **/
	private void wxLoadOil(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals(""))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("code") && (!vo.get("code").equals(""))) {
			constr = constr + " AND code LIKE '%" + BasicFunction.replaceSQL(vo.get("code")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr = constr + " AND carType='" + BasicFunction.replaceSQL(vo.get("carType")) + "'";
		}
		if (vo.containsKey("VIN") && (!vo.get("VIN").equals(""))) {
			constr = constr + " AND VIN LIKE '%" + BasicFunction.replaceSQL(vo.get("VIN")) + "%'";
		}
		if (vo.containsKey("engineNo") && (!vo.get("engineNo").equals(""))) {
			constr = constr + " AND engineNo LIKE '%" + BasicFunction.replaceSQL(vo.get("engineNo")) + "%'";
		}

		String sql = "SELECT * FROM v_oil WHERE isDel=0 AND userId=" + s.userid + constr + " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存油耗 **/
	private void saveOil(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("dateOrder"))vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("countDistance"))vo1.setProperty("countDistance", BasicFunction.replaceSQL(vo.get("countDistance")));
		if (vo.containsKey("countOil"))vo1.setProperty("countOil", BasicFunction.replaceSQL(vo.get("countOil")));
		if (vo.containsKey("price"))vo1.setProperty("price", BasicFunction.replaceSQL(vo.get("price")));
		if (vo.containsKey("priceCard"))vo1.setProperty("priceCard", BasicFunction.replaceSQL(vo.get("priceCard")));
		if (vo.containsKey("imagesId"))vo1.setProperty("imagesId", BasicFunction.replaceSQL(vo.get("imagesId")));
		
		vo1.setProperty("userId", s.userid+"");

		vo1.TableName = "t_oil";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}

		String sql = "SELECT *,0 AS rateOil FROM v_oil WHERE id=" + c_id;
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			r.put("rateOil", rs.getJSONObject(0).getString("rateOil"));
		}
	}

	// ***************************************************************************************
	/** 删除油耗 **/
	private void deleteOil(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_oil SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 保存车辆图片 **/
	private void saveOilImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));

		int imagesId = 0;
		imagesId = saveSysImages(vo, r);
		vo1.setProperty("imagesId", imagesId + "");

		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("dateOrder"))vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("countDistance"))vo1.setProperty("countDistance", BasicFunction.replaceSQL(vo.get("countDistance")));
		if (vo.containsKey("countOil"))vo1.setProperty("countOil", BasicFunction.replaceSQL(vo.get("countOil")));
		if (vo.containsKey("price"))vo1.setProperty("price", BasicFunction.replaceSQL(vo.get("price")));
		if (vo.containsKey("priceCard"))vo1.setProperty("priceCard", BasicFunction.replaceSQL(vo.get("priceCard")));

		vo1.TableName = "t_oil";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}

		String sql = "SELECT *,0 AS rateOil FROM v_oil WHERE id=" + c_id;
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			r.put("rateOil", rs.getJSONObject(0).getString("rateOil"));
		}
	}

	// ***************************************************************************************
	/** 加载车辆移交列表 **/
	private void loadCarChange(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("code") && (!vo.get("code").equals(""))) {
			constr = constr + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("code")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}

		String sql = "SELECT * FROM v_car_change WHERE isDel=0 AND isStop=0 " + constr
				+ " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存车辆移交 **/
	private void saveCarChange(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("departmentIdOld"))vo1.setProperty("departmentIdOld", BasicFunction.replaceSQL(vo.get("departmentIdOld")));
		if (vo.containsKey("departmentIdNew"))vo1.setProperty("departmentIdNew", BasicFunction.replaceSQL(vo.get("departmentIdNew")));
		if (vo.containsKey("dateOrder"))vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("remark"))vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));

		vo1.TableName = "t_car_change";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}

		updateCarDepartment(c_id + "");
	}

	// ***************************************************************************************
	/** 删除车辆移交 **/
	private void deleteCarChange(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_car_change SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);

		updateCarDepartment(id);
	}

	// ***************************************************************************************
	/** 保存车辆移交图片 **/
	private void saveCarChangeImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id"))throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));

		int imagesId = 0;
		imagesId = saveSysImages(vo, r);
		vo1.setProperty("imagesId", imagesId + "");

		if (vo.containsKey("carId"))vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("departmentIdOld"))vo1.setProperty("departmentIdOld", BasicFunction.replaceSQL(vo.get("departmentIdOld")));
		if (vo.containsKey("departmentIdNew"))vo1.setProperty("departmentIdNew", BasicFunction.replaceSQL(vo.get("departmentIdNew")));
		if (vo.containsKey("dateOrder"))vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("remark"))vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));
		vo1.setProperty("userId", s.userid+"");
		
		vo1.TableName = "t_car_change";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}
	}

	// ***************************************************************************************
	/** 更新关联车辆档案所属部门 **/
	private void updateCarDepartment(String c_id) throws Exception {
		String sql = "SELECT * FROM t_car_change WHERE id IN (" + c_id + ")";
		JSONArray rs = dao.fillRS(sql);
		for (int i = 0; i < rs.length(); i++) {
			String carId = rs.getJSONObject(i).getString("carId");

			sql = "SELECT * FROM t_car_change WHERE isDel=0 AND carId=" + carId
					+ " ORDER BY dateOrder DESC, id DESC LIMIT 1";
			JSONArray rs2 = dao.fillRS(sql);
			if (rs2.length() > 0) {
				sql = "UPDATE t_car SET departmentId=" + rs2.getJSONObject(0).getString("departmentIdNew")
						+ " WHERE id=" + carId;
				dao.execute(sql);
			}
		}
	}

	// ***************************************************************************************
	/** 保存车辆图片 **/
	private void saveCarImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("carId"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));

		int imagesId = 0;
		imagesId = saveSysImages(vo, r);
		vo1.setProperty("imagesId", imagesId + "");
		vo1.TableName = "t_car_images";
		dao.add(vo1);
	}

	// ***************************************************************************************
	/**
	 * 保存图片
	 * 
	 * @return
	 **/
	private int saveSysImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("srcL")) throw new Exception("缺少参数");
		if (!vo.containsKey("imagesTypeId")) throw new Exception("缺少参数");

		VO vo1 = new VO();
		String imagesTypeId = BasicFunction.replaceSQL(vo.get("imagesTypeId"));
		String srcL = BasicFunction.replaceSQL(vo.get("srcL"));
		String tagL = "";
		String tagS = "";
		int widthL = 0;
		int heightL = 0;
		int widthS = 0;
		int heightS = 0;
		String sourcePath = "";
		String savePath = "";
		String savePath_Pre = "";

		tagL = srcL;
		tagS = "preview/" + srcL;
		String sql = "SELECT id,IFNULL(uploadPath,'') AS uploadPath,isPreview,isChangeSize,widthL,heightL,widthS,heightS FROM t_sys_images_type WHERE id="
				+ imagesTypeId;
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() > 0) {
			String path = rs.getJSONObject(0).getString("uploadPath");
			boolean isPreview = rs.getJSONObject(0).getBoolean("isPreview");
			boolean isChangeSize = rs.getJSONObject(0).getBoolean("isChangeSize");
			widthL = rs.getJSONObject(0).getInt("widthL");
			heightL = rs.getJSONObject(0).getInt("heightL");
			widthS = rs.getJSONObject(0).getInt("widthS");
			heightS = rs.getJSONObject(0).getInt("heightS");

			sourcePath = s.config.UPLOAD_PATH + "/temp/";
			savePath = s.config.UPLOAD_PATH + "/images/upload/";
			savePath_Pre = s.config.UPLOAD_PATH + "/images/upload/preview/";

			if (path.length() > 0) {
				tagL = path + "/" + srcL;
				savePath_Pre = savePath + path + "/preview/";
				tagS = path + "/preview/" + srcL;
			}

			File dirFile = new File(savePath + path + "/");
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			// 剪切文件
			BasicFunction.moveFile(sourcePath + srcL, savePath + tagL);

			if (isChangeSize) {
				BasicFunction.resetImageSize(savePath + tagL, widthL, heightL);
			}
			if (isPreview) {
				File dirFile2 = new File(savePath_Pre);
				if (!dirFile2.exists()) {
					dirFile2.mkdirs();
				}
				BasicFunction.copyFile(savePath + tagL, savePath_Pre + srcL);
				BasicFunction.resetImageSize(savePath_Pre + srcL, widthS, heightS);

				File img = new File(savePath_Pre + srcL);
				BufferedImage bi = ImageIO.read(img);
				widthS = bi.getWidth();
				heightS = bi.getHeight();

				vo1.setProperty("srcS", tagS);
				vo1.setProperty("widthS", widthS + "");
				vo1.setProperty("heightS", heightS + "");
			}
		}

		File img = new File(savePath + tagL);
		BufferedImage bi = ImageIO.read(img);
		widthL = bi.getWidth();
		heightL = bi.getHeight();

		int c_id = 0;
		if (vo.containsKey("imagesTypeId"))
			vo1.setProperty("imagesTypeId", imagesTypeId);
		if (vo.containsKey("name"))
			vo1.setProperty("name", BasicFunction.replaceSQL(vo.get("name")));
		if (vo.containsKey("srcL"))
			vo1.setProperty("srcL", tagL);
		vo1.setProperty("widthL", widthL + "");
		vo1.setProperty("heightL", heightL + "");

		vo1.TableName = "t_sys_images";
		c_id = dao.add(vo1);

		return c_id;
	}

	// ***************************************************************************************
	/** 删除车辆图片 **/
	private void deleteCarImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("imagesId"))
			throw new Exception("缺少参数");
		if (!vo.containsKey("carId"))
			throw new Exception("缺少参数");
		String imagesId = BasicFunction.replaceSQL(vo.get("imagesId"));
		String carId = BasicFunction.replaceSQL(vo.get("carId"));

		String sql = "UPDATE t_car_images SET isDel=1 WHERE imagesId=" + imagesId + " AND carId=" + carId;
		dao.execute(sql);

		sql = "SELECT * FROM t_car_images WHERE isDel=0 AND imagesId=" + imagesId;
		JSONArray rs = dao.fillRS(sql);
		if (rs.length() == 0) {
			sql = "UPDATE t_sys_images SET isDel=1 WHERE id=" + imagesId;
			dao.execute(sql);

			deleteSysImagesFile(vo, r, imagesId);
		}
	}

	// ***************************************************************************************
	/** 加载车辆图片列表 **/
	private void loadCarImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("carId") && (!vo.get("carId").equals(""))) {
			constr = constr + " AND carId=" + BasicFunction.replaceSQL(vo.get("carId"));
		}

		String sql = "SELECT * FROM v_car_images WHERE isDel=0 AND isStop=0 " + constr + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载系统相册 **/
	private void loadSysImagesType(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String sql = "SELECT * FROM t_sys_images_type WHERE isDel=0 ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存系统相册 **/
	private void saveSysImagesType(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("name"))
			vo1.setProperty("name", BasicFunction.replaceSQL(vo.get("name")));
		if (vo.containsKey("remark"))
			vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));
		if (vo.containsKey("widthL"))
			vo1.setProperty("widthL", BasicFunction.replaceSQL(vo.get("widthL")));
		if (vo.containsKey("heightL"))
			vo1.setProperty("heightL", BasicFunction.replaceSQL(vo.get("heightL")));
		if (vo.containsKey("widthS"))
			vo1.setProperty("widthS", BasicFunction.replaceSQL(vo.get("widthS")));
		if (vo.containsKey("heightS"))
			vo1.setProperty("heightS", BasicFunction.replaceSQL(vo.get("heightS")));
		if (vo.containsKey("uploadPath"))
			vo1.setProperty("uploadPath", BasicFunction.replaceSQL(vo.get("uploadPath")));
		if (vo.containsKey("isChangeSize"))
			vo1.setProperty("isChangeSize", BasicFunction.replaceSQL(vo.get("isChangeSize")));
		if (vo.containsKey("isPreview"))
			vo1.setProperty("isPreview", BasicFunction.replaceSQL(vo.get("isPreview")));

		vo1.TableName = "t_sys_images_type";
		if (id > 0) {
			vo1.id = id;
			dao.update(vo1);
		} else {
			dao.add(vo1);
		}
	}

	// ***************************************************************************************
	/** 加载系统相册 **/
	private void loadSysImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("type") && !vo.get("type").equals("")) {
			constr = " AND imagesTypeId=" + BasicFunction.replaceSQL(vo.get("type"));
		}
		if (vo.containsKey("keyword") && !vo.get("keyword").equals("")) {
			constr = constr + " AND `name` LIKE '%" + BasicFunction.replaceSQL(vo.get("keyword")) + "%'";
		}

		String sql = "SELECT *, ifnull(`alt`,`name`) AS alt, CONCAT('" + s.config.DOMAIN_NAME
				+ "images/upload/',IF((`srcS` is null || `srcS`=''),`srcL`,`srcS`)) AS src FROM v_sys_images WHERE isDel=0 "
				+ constr + " ORDER BY id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存系统图片——文本形式 **/
	private void saveSysImages2(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");

		VO vo1 = new VO();
		int id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("imagesTypeId"))
			vo1.setProperty("imagesTypeId", BasicFunction.replaceSQL(vo.get("imagesTypeId")));
//		if(vo.containsKey("srcL"))vo1.setProperty("srcL", BasicFunction.replaceSQL(vo.get("srcL")));
//		if(vo.containsKey("srcS"))vo1.setProperty("srcS", BasicFunction.replaceSQL(vo.get("srcS")));
//		if(vo.containsKey("widthL"))vo1.setProperty("widthL", BasicFunction.replaceSQL(vo.get("widthL")));
//		if(vo.containsKey("heightL"))vo1.setProperty("heightL", BasicFunction.replaceSQL(vo.get("heightL")));
//		if(vo.containsKey("widthS"))vo1.setProperty("widthS", BasicFunction.replaceSQL(vo.get("widthS")));
//		if(vo.containsKey("heightS"))vo1.setProperty("heightS", BasicFunction.replaceSQL(vo.get("heightS")));
		if (vo.containsKey("name"))
			vo1.setProperty("name", BasicFunction.replaceSQL(vo.get("name")));
//		if(vo.containsKey("alt"))vo1.setProperty("alt", BasicFunction.replaceSQL(vo.get("alt")));

		vo1.TableName = "t_sys_images";
		if (id > 0) {
			vo1.id = id;
			dao.update(vo1);
		} else {
			dao.add(vo1);
		}
	}

	// ***************************************************************************************
	/** 微信小程序保存系统图片 **/
	private void wxSaveSysImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		//保存图片文件到服务器临时文件夹
		String fileBase64=BasicFunction.replaceSQL(vo.get("fileBase64"));
		byte[] file=Base64.decodeBase64(fileBase64);
		
		String savePath = s.config.UPLOAD_PATH + "/temp/"; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
	    String ymd = sdf.format(new Date());  
	    savePath += "/" + ymd + "/";  
	    File dirFile = new File(savePath);  
	    if (!dirFile.exists()) {  
	    	dirFile.mkdirs();  
	    }  
	    
	    int imagesId=0;
	    String extName=BasicFunction.replaceSQL(vo.get("extName"));
        try {  
        	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
            
            File uploadedFile = new File(savePath, newFileName + extName);  
            OutputStream os = new FileOutputStream(uploadedFile);  
            os.write(file, 0, file.length);    
            os.flush();  
            os.close();     
            
            vo.put("srcL", ymd + "/" + newFileName + extName);
            vo.put("name", newFileName);
            imagesId = saveSysImages(vo, r);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        r.put("imagesId", imagesId);
        
        String srcS="";
        String sql="SELECT * FROM t_sys_images WHERE id=" + imagesId;
        JSONArray rs = dao.fillRS(sql);
        if(rs.length()>0) {
        	srcS = rs.getJSONObject(0).getString("srcS");
        }
        r.put("srcS", srcS);
	}

	// ***************************************************************************************
	/** 删除图片 **/
	private void deleteSysImages(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id"))
			throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_sys_images SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);

		deleteSysImagesFile(vo, r, id);
	}

	// ***************************************************************************************
	/** 物理删除图片文件 **/
	private void deleteSysImagesFile(Map<String, String> vo, JSONObject r, String id) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		// 物理删除图片文件
		String sql = "SELECT * FROM t_sys_images WHERE isDel=1 AND id IN (" + id + ")";
		JSONArray rs = dao.fillRS(sql);
		for (int i = 0; i < rs.length(); i++) {
			String srcL = s.config.UPLOAD_PATH + "/images/upload/" + rs.getJSONObject(i).getString("srcL");
			String srcS = s.config.UPLOAD_PATH + "/images/upload/" + rs.getJSONObject(i).getString("srcS");
			File picL = new File(srcL);
			File picS = new File(srcS);
			if (picL.exists()) {
				picL.delete();
			}
			if (picS.exists()) {
				picS.delete();
			}
		}
	}

	// ***************************************************************************************
	/** 加载领料单列表 **/
	private void loadStorageOut(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOut") && (!vo.get("dateOut").equals("")) && (!vo.get("dateOut").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOut"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOut BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("type") && (!vo.get("type").equals(""))) {
			constr = constr + " AND type IN (" + BasicFunction.replaceSQL(vo.get("type")) + ")";
		}
		if (vo.containsKey("orderCode") && (!vo.get("orderCode").equals(""))) {
			constr = constr + " AND orderCode LIKE '%" + BasicFunction.replaceSQL(vo.get("orderCode")) + "%'";
		}
		if (vo.containsKey("orderCodeRepair") && (!vo.get("orderCodeRepair").equals(""))) {
			constr = constr + " AND orderCodeRepair LIKE '%" + BasicFunction.replaceSQL(vo.get("orderCodeRepair"))
					+ "%'";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr = constr + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr = constr + " AND department = '" + BasicFunction.replaceSQL(vo.get("department")) + "'";
		}
		if (vo.containsKey("employee") && (!vo.get("employee").equals(""))) {
			constr = constr + " AND employee LIKE '%" + BasicFunction.replaceSQL(vo.get("employee")) + "%'";
		}
		if (vo.containsKey("warehouseId") && (!vo.get("warehouseId").equals(""))) {
			constr = constr + " AND warehouseId=" + BasicFunction.replaceSQL(vo.get("warehouseId"));
		}
		if (vo.containsKey("warehouse") && (!vo.get("warehouse").equals(""))) {
			constr = constr + " AND warehouse LIKE '%" + BasicFunction.replaceSQL(vo.get("warehouse")) + "%'";
		}
		if (vo.containsKey("remark") && (!vo.get("remark").equals(""))) {
			constr = constr + " AND remark LIKE '%" + BasicFunction.replaceSQL(vo.get("remark")) + "%'";
		}
		if (vo.containsKey("carDetail") && (!vo.get("carDetail").equals(""))) {
			constr = constr + " AND id in(SELECT orderId FROM t_storage_out_detail WHERE car LIKE '%" + BasicFunction.replaceSQL(vo.get("carDetail")) + "%')";
		}
		if (vo.containsKey("itemCode") && (!vo.get("itemCode").equals(""))) {
			constr = constr + " AND id in(SELECT orderId FROM t_storage_out_detail WHERE itemCode LIKE '%" + BasicFunction.replaceSQL(vo.get("itemCode")) + "%')";
		}
		if (vo.containsKey("itemName") && (!vo.get("itemName").equals(""))) {
			constr = constr + " AND id in(SELECT orderId FROM t_storage_out_detail WHERE itemName LIKE '%" + BasicFunction.replaceSQL(vo.get("itemName")) + "%')";
		}
		if (vo.containsKey("spec") && (!vo.get("spec").equals(""))) {
			constr = constr + " AND id in(SELECT orderId FROM t_storage_out_detail WHERE spec LIKE '%" + BasicFunction.replaceSQL(vo.get("spec")) + "%')";
		}

		String sql = "SELECT *, (SELECT GROUP_CONCAT(t_repair_detail.project) FROM t_repair_detail WHERE t_repair_detail.isDel=0 AND t_repair_detail.orderId=v_storage_out.repairId) AS project FROM v_storage_out WHERE isDel=0" + constr + " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载领料单详情列表 **/
	private void loadStorageOutDetail(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("editId"))
			throw new Exception("缺少参数");

		String constr = "";
		constr = constr + " AND orderId=" + BasicFunction.replaceSQL(vo.get("editId"));

		String sql = "SELECT * FROM t_storage_out_detail WHERE isDel=0 AND isStop=0 " + constr + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载报修单领料汇总列表 **/
	private void loadStorageOutDetailByRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("orderCodeRepair"))
			throw new Exception("缺少参数");

		String constr = "";
		constr = constr + " AND orderCodeRepair='" + BasicFunction.replaceSQL(vo.get("orderCodeRepair")) + "'";
		if (vo.containsKey("isCheckedStorageOut") && vo.get("isCheckedStorageOut").equals("true")) constr = constr + " AND isCheckedStorageOut=1";

		String sql = "SELECT * FROM v_storage_out_detail WHERE isDel=0" + constr + " ORDER BY id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存领料单 **/
	private void saveStorageOut(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		if (!vo.containsKey("type")) throw new Exception("缺少参数");
		if (!vo.containsKey("orderCodeRepair")) throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		int type = Integer.parseInt(vo.get("type"));
		String orderCodeRepair = BasicFunction.replaceSQL(vo.get("orderCodeRepair"));
		
		vo1.setProperty("type", type+"");
		vo1.setProperty("orderCodeRepair", orderCodeRepair);
		if (vo.containsKey("dateOut")) vo1.setProperty("dateOut", BasicFunction.replaceSQL(vo.get("dateOut")));
		if (vo.containsKey("departmentId")) vo1.setProperty("departmentId", BasicFunction.replaceSQL(vo.get("departmentId")));
		if (vo.containsKey("department")) vo1.setProperty("department", BasicFunction.replaceSQL(vo.get("department")));
		if (vo.containsKey("employeeId")) vo1.setProperty("employeeId", BasicFunction.replaceSQL(vo.get("employeeId")));
		if (vo.containsKey("employee")) vo1.setProperty("employee", BasicFunction.replaceSQL(vo.get("employee")));
		if (vo.containsKey("warehouseId")) vo1.setProperty("warehouseId", BasicFunction.replaceSQL(vo.get("warehouseId")));
		if (vo.containsKey("warehouse")) vo1.setProperty("warehouse", BasicFunction.replaceSQL(vo.get("warehouse")));
		if (vo.containsKey("remark")) vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));
		vo1.setProperty("userId", s.userid+"");
		
		vo1.TableName = "t_storage_out";
		if(type==1) {
			String sql = "SELECT id FROM t_storage_out WHERE isDel=0 AND isChecked=0 AND orderCodeRepair='"+orderCodeRepair+"'";
			JSONArray rs = dao.fillRS(sql);
			if(rs.length()==0) {
				c_id = dao.add(vo1);
			}else {
				c_id = rs.getJSONObject(0).getInt("id");
			}
		}else {
			if (c_id > 0) {
				vo1.id = c_id;
				dao.update(vo1);
			} else {
				// 获取当时时间
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date(System.currentTimeMillis());
				String dateOrder = dateFormat.format(now);
				vo1.setProperty("dateOrder", dateOrder);
//
//				// 获取自动生成单号
//				String orderCode = "";
//				String sql = "SELECT CONCAT(" + date2 + ", LPAD(FLOOR(RAND() * 1000),3,0)) AS name";
//				JSONArray rs = dao.fillRS(sql);
//				if (rs.length() > 0) {
//					orderCode = rs.getJSONObject(0).getString("name");
//				}
//				vo1.setProperty("orderCode", orderCode);

				c_id = dao.add(vo1);
				r.put("addedId", c_id);
//				r.put("orderCode", orderCode);
				r.put("dateOrder", dateOrder);
			}
		}
		
		// 删除详细表
		String sql = "DELETE FROM t_storage_out_detail WHERE orderId=" + c_id;
		dao.execute(sql);

		// 保存详细表
		JSONArray detail = new JSONArray(BasicFunction.replaceSQL(vo.get("detail")));
		for (int i = 0; i < detail.length(); i++) {
			JSONObject itemDetail = detail.getJSONObject(i);

			if(type==1 && itemDetail.getString("isCheckedStorageOut").equals("true")) {
				
			}else {
				VO vo2 = new VO();
				vo2.TableName = "t_storage_out_detail";
				vo2.setProperty("orderId", c_id + "");
				vo2.setProperty("itemId", itemDetail.getString("itemId"));
				vo2.setProperty("itemCode", itemDetail.getString("itemCode"));
				vo2.setProperty("itemName", itemDetail.getString("itemName"));
				vo2.setProperty("spec", itemDetail.getString("spec"));
				vo2.setProperty("unit", itemDetail.getString("unit"));
				vo2.setProperty("count", itemDetail.getString("count"));
				vo2.setProperty("countStorage", itemDetail.getString("countStorage"));
				vo2.setProperty("countWarn", itemDetail.getString("countWarn"));
				vo2.setProperty("unitprice", itemDetail.getString("unitprice"));
				vo2.setProperty("carId", itemDetail.getString("carId"));
				vo2.setProperty("car", itemDetail.getString("car"));
				dao.add(vo2);
			}
		}
	}

	// ***************************************************************************************
	/** 删除领料单 **/
	private void deleteStorageOut(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_storage_out SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);

		sql = "UPDATE t_storage_out_detail SET isDel=1 WHERE orderId IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 审核领料单 **/
	private void checkStorageOut(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		if (!vo.containsKey("type")) throw new Exception("缺少参数");
		
		int id = Integer.parseInt(vo.get("id"));
		int type = Integer.parseInt(vo.get("type"));
		
		String orderCodeRepair = "";
		if (vo.containsKey("orderCodeRepair")) orderCodeRepair=BasicFunction.replaceSQL(vo.get("orderCodeRepair"));

		if(type==1) {
			String sql = "SELECT * FROM t_storage_out WHERE orderCodeRepair='" + orderCodeRepair + "' AND isChecked=0 AND isDel=0";
			JSONArray rs = dao.fillRS(sql);
			for(int i=0;i<rs.length();i++) {
				int c_id = rs.getJSONObject(i).getInt("id");
				
				VO vo1 = new VO();
				if (vo.containsKey("orderCode")) vo1.setProperty("orderCode", BasicFunction.replaceSQL(vo.get("orderCode")));
				if (vo.containsKey("dateOut")) vo1.setProperty("dateOut", BasicFunction.replaceSQL(vo.get("dateOut")));
				if (vo.containsKey("departmentId")) vo1.setProperty("departmentId", BasicFunction.replaceSQL(vo.get("departmentId")));
				if (vo.containsKey("department")) vo1.setProperty("department", BasicFunction.replaceSQL(vo.get("department")));
//				if (vo.containsKey("employeeId")) vo1.setProperty("employeeId", BasicFunction.replaceSQL(vo.get("employeeId")));
				if (vo.containsKey("employee")) vo1.setProperty("employee", BasicFunction.replaceSQL(vo.get("employee")));
				if (vo.containsKey("warehouseId")) vo1.setProperty("warehouseId", BasicFunction.replaceSQL(vo.get("warehouseId")));
				if (vo.containsKey("warehouse")) vo1.setProperty("warehouse", BasicFunction.replaceSQL(vo.get("warehouse")));
				if (vo.containsKey("remark")) vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));
				if (vo.containsKey("dateOrder"))  vo1.setProperty("dateOrder", vo.get("dateOrder"));
				vo1.setProperty("isChecked", "1");
				vo1.id = c_id;
				vo1.TableName = "t_storage_out";
				dao.update(vo1);
				
				sql = "UPDATE t_storage_out_detail SET isChecked=1 WHERE orderId=" + c_id;
				dao.execute(sql);
			}
		}else {
			VO vo1 = new VO();
			if (vo.containsKey("orderCode")) vo1.setProperty("orderCode", BasicFunction.replaceSQL(vo.get("orderCode")));
			if (vo.containsKey("dateOrder"))  vo1.setProperty("dateOrder", vo.get("dateOrder"));
			vo1.setProperty("isChecked", "1");
			vo1.id = id;
			vo1.TableName = "t_storage_out";
			dao.update(vo1);
			
			String sql = "UPDATE t_storage_out_detail SET isChecked=1 WHERE orderId=" + id;
			dao.execute(sql);
		}
	}

	// ***************************************************************************************
	/** 反审领料单 **/
	private void uncheckStorageOut(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		if (!vo.containsKey("type")) throw new Exception("缺少参数");
		
		String id = BasicFunction.replaceSQL(vo.get("id"));
		int type = Integer.parseInt(vo.get("type"));
		
		String orderCode = "";			//用友领料单号
		if (vo.containsKey("orderCode")) orderCode=BasicFunction.replaceSQL(vo.get("orderCode"));
		
		if(type==1) {
			String sql = "SELECT * FROM t_storage_out WHERE orderCode='" + orderCode + "' AND isChecked=1 AND isDel=0";
			JSONArray rs = dao.fillRS(sql);
			if(rs.length()>0) {
				int c_id = rs.getJSONObject(0).getInt("id");
				sql = "UPDATE t_storage_out SET isChecked=0,orderCode='',dateOrder=null,dateOut=null,departmentId=0,department='',employeeId=0,employee='',warehouseId=0,warehouse='',remark='' WHERE id=" + c_id;
				dao.execute(sql);
				
				sql = "UPDATE t_storage_out_detail SET isChecked=0 WHERE orderId=" + c_id;
				dao.execute(sql);
				
				//把之前未审的单与新反审的单合并
				sql = "SELECT * FROM t_storage_out WHERE isDel=0 AND isChecked=0 AND orderCodeRepair='" + rs.getJSONObject(0).getString("orderCodeRepair") + "' AND id<>" + c_id;
				JSONArray rs2 = dao.fillRS(sql);
				for(int i=0;i<rs2.length();i++) {
					int c_orderId = rs2.getJSONObject(i).getInt("id");
					sql = "UPDATE t_storage_out_detail SET orderId=" + c_id + " WHERE orderId=" + c_orderId;
					dao.execute(sql);
					sql = "DELETE FROM t_storage_out WHERE id=" + c_orderId;
					dao.execute(sql);
				}
				
				sql = "SELECT * FROM t_storage_out_detail WHERE orderId=" + c_id;
				JSONArray rs3 = dao.fillRS(sql);
				if(rs3.length()==0) {
					sql = "DELETE FROM t_storage_out WHERE id=" + c_id;
					dao.execute(sql);
				}
			}
		}else {
			String sql = "UPDATE t_storage_out SET isChecked=0,orderCode='' WHERE id=" + id;
			dao.execute(sql);
			sql = "UPDATE t_storage_out_detail SET isChecked=0 WHERE orderId=" + id;
			dao.execute(sql);
		}
	}
	
	// ***************************************************************************************
	/** 获取截数日期 **/
	private void getStorageOutDate(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String dateStorageOut="";
		
		String sql = "SELECT * FROM t_storage_out_date ORDER BY date DESC LIMIT 1";
		JSONArray rs = dao.fillRS(sql);
		if(rs.length()>0) {
			dateStorageOut = rs.getJSONObject(0).getString("date").replace(" 00:00:00.0", "");
		}
		r.put("dateStorageOut", dateStorageOut);
	}


	// ***************************************************************************************
	/** 加载车辆维修记录列表 **/
	private void loadReportRepair(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr1 = "";
		String constr2 = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals("")) && (!vo.get("dateOrder").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr1 = constr1 + " AND (t_repair.dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("dateBegin") && (!vo.get("dateBegin").equals("")) && (!vo.get("dateBegin").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateBegin"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr1 = constr1 + " AND (t_repair.dateBegin BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("dateEnd") && (!vo.get("dateEnd").equals("")) && (!vo.get("dateEnd").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateEnd"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr1 = constr1 + " AND (t_repair.dateEnd BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("orderCode") && (!vo.get("orderCode").equals(""))) {
			constr1 = constr1 + " AND t_repair.orderCode LIKE '%" + BasicFunction.replaceSQL(vo.get("orderCode")) + "%'";
		}
		if (vo.containsKey("group") && (!vo.get("group").equals(""))) {
			constr1 = constr1 + " AND t_repair.`group` LIKE '%" + BasicFunction.replaceSQL(vo.get("group")) + "%'";
		}
		if (vo.containsKey("employee") && (!vo.get("employee").equals(""))) {
			constr1 = constr1 + " AND t_repair.`employee` LIKE '%" + BasicFunction.replaceSQL(vo.get("employee")) + "%'";
		}
		if (vo.containsKey("repairTypeId") && (!vo.get("repairTypeId").equals(""))) {
			constr1 = constr1 + " AND t_repair.repairTypeId IN (" + BasicFunction.replaceSQL(vo.get("repairTypeId")) + ")";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr2 = constr2 + " AND v_repair.carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr2 = constr2 + " AND v_repair.carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr2 = constr2 + " AND v_repair.carType IN ('" + BasicFunction.replaceSQL(vo.get("carType")).replaceAll(",", "','") + "')";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr2 = constr2 + " AND v_repair.department LIKE '%" + BasicFunction.replaceSQL(vo.get("department")) + "%'";
		}
		if (vo.containsKey("customerId") && (!vo.get("customerId").equals(""))) {
			constr1 = constr1 + " AND t_repair.customerId=" + BasicFunction.replaceSQL(vo.get("customerId"));
		}
		if (vo.containsKey("dump") && (!vo.get("dump").equals(""))) {
			constr2 = constr2 + " AND v_repair.dump LIKE '%" + BasicFunction.replaceSQL(vo.get("dump")) + "%'";
		}
		if (vo.containsKey("dumpTypeId") && (!vo.get("dumpTypeId").equals(""))) {
			constr2 = constr2 + " AND v_repair.dumpTypeId IN (" + BasicFunction.replaceSQL(vo.get("dumpTypeId")) + ")";
		}
		if (vo.containsKey("remark") && (!vo.get("remark").equals(""))) {
			constr1 = constr1 + " AND t_repair.remark LIKE '%" + BasicFunction.replaceSQL(vo.get("remark")) + "%'";
		}
		if (vo.containsKey("isChecked") && (!vo.get("isChecked").equals(""))) {
			if (vo.get("isChecked").equals("true")) {
				constr1 = constr1 + " AND t_repair.isChecked=1";
			} else {
				constr1 = constr1 + " AND t_repair.isChecked=0";
			}
		}
		if (vo.containsKey("project") && (!vo.get("project").equals(""))) {
			constr2 = constr2 + " AND temp_repair.project LIKE '%" + BasicFunction.replaceSQL(vo.get("project")) + "%'";
		}
		if (vo.containsKey("employeeRepair") && (!vo.get("employeeRepair").equals(""))) {
			constr2 = constr2 + " AND temp_repair.employeeRepair LIKE '%" + BasicFunction.replaceSQL(vo.get("employeeRepair")) + "%'";
		}
		if (vo.containsKey("itemCode") && (!vo.get("itemCode").equals(""))) {
			constr2 = constr2 + " AND temp_storage.storageOut LIKE '%" + BasicFunction.replaceSQL(vo.get("itemCode")) + "%'";
		}
		if (vo.containsKey("itemName") && (!vo.get("itemName").equals(""))) {
			constr2 = constr2 + " AND temp_storage.storageOut LIKE '%" + BasicFunction.replaceSQL(vo.get("itemName")) + "%'";
		}

		String sql = "SELECT temp_repair.id,temp_repair.orderCode,DATE_FORMAT(v_repair.dateOrder, '%Y-%m-%d') AS dateOrder,v_repair.carCode,v_repair.carNo,v_repair.carType,v_repair.`group`,v_repair.employee,v_repair.remark";
		sql=sql+",v_repair.isChecked,v_repair.repairTypeId,v_repair.repairType,v_repair.customerId,v_repair.department,DATE_FORMAT(v_repair.dateBegin, '%Y-%m-%d') AS dateBegin,DATE_FORMAT(v_repair.dateEnd, '%Y-%m-%d') AS dateEnd";
		sql=sql+",v_repair.countDistance1,v_repair.countDistance2,v_repair.dumpName,v_repair.`user`,temp_repair.project,temp_repair.hours,temp_repair.employeeRepair,temp_storage.storageOut";
		sql=sql+" FROM (SELECT t_repair.id,t_repair.orderCode,GROUP_CONCAT(t_repair_detail.project) AS project,GROUP_CONCAT(t_repair_detail.hours) AS hours,GROUP_CONCAT(t_repair_detail.employee) AS employeeRepair";
		sql=sql+" FROM t_repair INNER JOIN t_repair_detail ON t_repair_detail.orderId=t_repair.id AND t_repair_detail.isDel=0";
		sql=sql+" WHERE t_repair.isDel=0 " + constr1;
		sql=sql+" GROUP BY t_repair.id,t_repair.orderCode) AS temp_repair";
		sql=sql+" LEFT OUTER JOIN (SELECT t_repair.id AS repairId,GROUP_CONCAT(CONCAT(t_storage_out_detail.itemName,'*',t_storage_out_detail.`count`)) AS storageOut";
		sql=sql+" FROM t_repair INNER JOIN t_storage_out ON t_storage_out.orderCodeRepair=t_repair.orderCode INNER JOIN t_storage_out_detail ON t_storage_out_detail.orderId=t_storage_out.id";
		sql=sql+" WHERE t_repair.isDel=0 " + constr1;
		sql=sql+" GROUP BY t_repair.id) AS temp_storage ON temp_repair.id=temp_storage.repairId";
		sql=sql+" INNER JOIN v_repair ON v_repair.id= temp_repair.id";
		sql=sql+" WHERE 1=1 " + constr2;
		sql=sql+" ORDER BY v_repair.dateOrder DESC,v_repair.id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}


	// ***************************************************************************************
	/** 加载报购单列表 **/
	private void loadStorageIn(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals("")) && (!vo.get("dateOrder").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr = constr + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("itemName") && (!vo.get("itemName").equals(""))) {
			constr = constr + " AND itemName LIKE '%" + BasicFunction.replaceSQL(vo.get("itemName")) + "%'";
		}
		if (vo.containsKey("spec") && (!vo.get("spec").equals(""))) {
			constr = constr + " AND spec LIKE '%" + BasicFunction.replaceSQL(vo.get("spec")) + "%'";
		}
		if (vo.containsKey("itemCode") && (!vo.get("itemCode").equals(""))) {
			constr = constr + " AND itemCode LIKE '%" + BasicFunction.replaceSQL(vo.get("itemCode")) + "%'";
		}
		if (vo.containsKey("group") && (!vo.get("group").equals(""))) {
			constr = constr + " AND `group` LIKE '%" + BasicFunction.replaceSQL(vo.get("group")) + "%'";
		}
		if (vo.containsKey("customer") && (!vo.get("customer").equals(""))) {
			constr = constr + " AND customer LIKE '%" + BasicFunction.replaceSQL(vo.get("customer")) + "%'";
		}
		if (vo.containsKey("warehouse") && (!vo.get("warehouse").equals(""))) {
			constr = constr + " AND warehouse LIKE '%" + BasicFunction.replaceSQL(vo.get("warehouse")) + "%'";
		}

		String sql = "SELECT * FROM v_storage_in WHERE isDel=0 " + constr
				+ " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存报购单 **/
	private void saveStorageIn(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id")) throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("carId")) vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("itemName")) vo1.setProperty("itemName", BasicFunction.replaceSQL(vo.get("itemName")));
		if (vo.containsKey("spec")) vo1.setProperty("spec", BasicFunction.replaceSQL(vo.get("spec")));
		if (vo.containsKey("count")) vo1.setProperty("count", BasicFunction.replaceSQL(vo.get("count")));
		if (vo.containsKey("group")) vo1.setProperty("group", BasicFunction.replaceSQL(vo.get("group")));
		if (vo.containsKey("dateOrder")) vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("customer")) vo1.setProperty("customer", BasicFunction.replaceSQL(vo.get("customer")));
		if (vo.containsKey("dateArrival")) vo1.setProperty("dateArrival", BasicFunction.replaceSQL(vo.get("dateArrival")));
		if (vo.containsKey("itemCode")) vo1.setProperty("itemCode", BasicFunction.replaceSQL(vo.get("itemCode")));
		if (vo.containsKey("warehouseId")) vo1.setProperty("warehouseId", BasicFunction.replaceSQL(vo.get("warehouseId")));
		if (vo.containsKey("warehouse")) vo1.setProperty("warehouse", BasicFunction.replaceSQL(vo.get("warehouse")));
		if (vo.containsKey("remark")) vo1.setProperty("remark", BasicFunction.replaceSQL(vo.get("remark")));

		vo1.TableName = "t_storage_in";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}

		updateCarDepartment(c_id + "");
	}

	// ***************************************************************************************
	/** 删除报购单 **/
	private void deleteStorageIn(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_storage_in SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);
	}


	// ***************************************************************************************
	/** 加载尿素列表 **/
	private void loadUrea(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals("")) && (!vo.get("dateOrder").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr = constr + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr = constr + " AND carType LIKE '%" + BasicFunction.replaceSQL(vo.get("carType")) + "%'";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr = constr + " AND department LIKE '%" + BasicFunction.replaceSQL(vo.get("spec")) + "%'";
		}
		
		String sql = "SELECT * FROM v_urea WHERE isDel=0 " + constr + " ORDER BY dateOrder DESC,id DESC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 保存尿素 **/
	private void saveUrea(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());
		if (!vo.containsKey("id")) throw new Exception("缺少参数");

		VO vo1 = new VO();
		int c_id = Integer.parseInt(vo.get("id"));
		if (vo.containsKey("carId")) vo1.setProperty("carId", BasicFunction.replaceSQL(vo.get("carId")));
		if (vo.containsKey("count")) vo1.setProperty("count", BasicFunction.replaceSQL(vo.get("count")));
		if (vo.containsKey("dateOrder")) vo1.setProperty("dateOrder", BasicFunction.replaceSQL(vo.get("dateOrder")));
		if (vo.containsKey("employee1")) vo1.setProperty("employee1", BasicFunction.replaceSQL(vo.get("employee1")));
		if (vo.containsKey("employee2")) vo1.setProperty("employee2", BasicFunction.replaceSQL(vo.get("employee2")));
		vo1.setProperty("userId", s.userid+"");

		vo1.TableName = "t_urea";
		if (c_id > 0) {
			vo1.id = c_id;
			dao.update(vo1);
		} else {
			c_id = dao.add(vo1);
			r.put("addedId", c_id);
		}
	}

	// ***************************************************************************************
	/** 删除尿素 **/
	private void deleteUrea(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		if (!vo.containsKey("id")) throw new Exception("缺少参数");
		String id = BasicFunction.replaceSQL(vo.get("id"));

		String sql = "UPDATE t_urea SET isDel=1 WHERE id IN (" + id + ")";
		dao.execute(sql);
	}

	// ***************************************************************************************
	/** 加载员工工时列表 **/
	private void loadReportEmployee(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals("")) && (!vo.get("dateOrder").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (v_repair_detail.dateEnd BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		
		String sql = "SELECT * FROM(";
		sql=sql+"SELECT `name` as employee,IFNULL((SELECT SUM(hours) FROM v_repair_detail WHERE v_repair_detail.isDel=0 " + constr + " AND v_repair_detail.employee LIKE CONCAT('%', t_employee.`name`, '%')),0) as hours FROM t_employee WHERE isDel=0";
		sql=sql+")t WHERE t.hours>0";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加领料明细汇总列表 **/
	private void loadReportStorageOutDetail(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr = "";
		if (vo.containsKey("dateOut") && (!vo.get("dateOut").equals("")) && (!vo.get("dateOut").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOut"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr = constr + " AND (dateOut BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("orderCode") && (!vo.get("orderCode").equals(""))) {
			constr = constr + " AND orderCode LIKE '%" + BasicFunction.replaceSQL(vo.get("orderCode")) + "%'";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr = constr + " AND department LIKE '%" + BasicFunction.replaceSQL(vo.get("department")) + "%'";
		}
		if (vo.containsKey("employee") && (!vo.get("employee").equals(""))) {
			constr = constr + " AND employee LIKE '%" + BasicFunction.replaceSQL(vo.get("employee")) + "%'";
		}
		if (vo.containsKey("warehouseId") && (!vo.get("warehouseId").equals(""))) {
			constr = constr + " AND warehouseId=" + BasicFunction.replaceSQL(vo.get("warehouseId"));
		}
		if (vo.containsKey("orderCodeRepair") && (!vo.get("orderCodeRepair").equals(""))) {
			constr = constr + " AND orderCodeRepair LIKE '%" + BasicFunction.replaceSQL(vo.get("orderCodeRepair"))
					+ "%'";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr = constr + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr = constr + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carDetail") && (!vo.get("carDetail").equals(""))) {
			constr = constr + " AND car LIKE '%" + BasicFunction.replaceSQL(vo.get("carDetail")) + "%'";
		}
		if (vo.containsKey("departmentCar") && (!vo.get("departmentCar").equals(""))) {
			constr = constr + " AND departmentCar LIKE '%" + BasicFunction.replaceSQL(vo.get("departmentCar")) + "%'";
		}
		if (vo.containsKey("dumpTypeId") && (!vo.get("dumpTypeId").equals(""))) {
			constr = constr + " AND dumpTypeId=" + BasicFunction.replaceSQL(vo.get("dumpTypeId"));
		}
		if (vo.containsKey("dump") && (!vo.get("dump").equals(""))) {
			constr = constr + " AND dump LIKE '%" + BasicFunction.replaceSQL(vo.get("dump")) + "%'";
		}
		if (vo.containsKey("itemCode") && (!vo.get("itemCode").equals(""))) {
			constr = constr + " AND itemCode LIKE '%" + BasicFunction.replaceSQL(vo.get("itemCode")) + "%'";
		}
		if (vo.containsKey("itemName") && (!vo.get("itemName").equals(""))) {
			constr = constr + " AND itemName LIKE '%" + BasicFunction.replaceSQL(vo.get("itemName")) + "%'";
		}
		if (vo.containsKey("spec") && (!vo.get("spec").equals(""))) {
			constr = constr + " AND spec LIKE '%" + BasicFunction.replaceSQL(vo.get("spec")) + "%'";
		}

		String sql = "SELECT orderCode,orderCodeRepair,dateOut,department,employee,warehouse,carCode,carNo,car AS carDetail,departmentCar,dump,itemCode,itemName,spec,`count`,unitprice,`count`*unitprice AS price,id FROM v_storage_out_detail WHERE isDel=0 " + constr;
		sql = sql + " ORDER BY orderCodeRepair,orderCode,id ASC";
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}

	// ***************************************************************************************
	/** 加载车辆明细汇总列表 **/
	private void loadReportCar(Map<String, String> vo, JSONObject r) throws Exception {
		MSession s = this.getSession(vo, DoServlet.getSessionList());

		String constr1 = "";
		String constr2 = "";
		if (vo.containsKey("dateOrder") && (!vo.get("dateOrder").equals("")) && (!vo.get("dateOrder").equals("null"))) {
			String strDate = BasicFunction.replaceSQL(vo.get("dateOrder"));
			String dateOrder1 = strDate.substring(0, strDate.indexOf(","));
			String dateOrder2 = strDate.substring(strDate.indexOf(",") + 1, strDate.length());
			constr1 = constr1 + " AND (dateOrder BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
			constr2 = constr2 + " AND (dateOut BETWEEN '" + dateOrder1 + "' AND '" + dateOrder2 + " 23:59:59')";
		}
		if (vo.containsKey("carCode") && (!vo.get("carCode").equals(""))) {
			constr1 = constr1 + " AND code LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
			constr2 = constr2 + " AND carCode LIKE '%" + BasicFunction.replaceSQL(vo.get("carCode")) + "%'";
		}
		if (vo.containsKey("carNo") && (!vo.get("carNo").equals(""))) {
			constr1 = constr1 + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
			constr2 = constr2 + " AND carNo LIKE '%" + BasicFunction.replaceSQL(vo.get("carNo")) + "%'";
		}
		if (vo.containsKey("carType") && (!vo.get("carType").equals(""))) {
			constr1 = constr1 + " AND carType='" + BasicFunction.replaceSQL(vo.get("carType")) + "'";
			constr2 = constr2 + " AND carType='" + BasicFunction.replaceSQL(vo.get("carType")) + "'";
		}
		if (vo.containsKey("department") && (!vo.get("department").equals(""))) {
			constr1 = constr1 + " AND department LIKE '%" + BasicFunction.replaceSQL(vo.get("department")) + "%'";
			constr2 = constr2 + " AND departmentCar LIKE '%" + BasicFunction.replaceSQL(vo.get("department")) + "%'";
		}

		String sql = "";
		sql = sql + " SELECT dateOrder,`code` AS carCode,carNo,carType,department,IF(`type`=1,'年审',IF(`type`=2,'保养',CONCAT('保险',IF(IFNULL(orderCode,'')='','',CONCAT('，',orderCode))))) AS content,1 as `count`,price as unitprice,price  FROM v_safety WHERE isDel=0 " + constr1;
		sql = sql + " UNION ALL SELECT dateOut AS dateOrder,carCode,carNo,carType,departmentCar AS department,CONCAT('维修，',itemName,' ',spec) AS content,`count`,unitprice,ROUND(`count`*unitprice,2) AS price FROM v_storage_out_detail WHERE isDel=0 " + constr2;
		JSONArray rs = dao.fillRS(sql);
		r.put("records", rs);
	}
	
	
}
